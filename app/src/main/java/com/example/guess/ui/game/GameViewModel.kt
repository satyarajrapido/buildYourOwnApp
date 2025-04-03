package com.example.guess.ui.game

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.guess.data.GameChatMessage
import com.example.guess.utils.KEY_HOST_NAME
import com.example.guess.utils.KEY_NAME
import com.example.guess.utils.KEY_SELECTED_WORD
import com.example.guess.utils.groupId
import com.example.guess.utils.groupName
import com.example.guess.utils.hostName
import com.example.guess.utils.selectedWord
import com.example.guess.utils.toBase64String
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.channel.subscribeFor
import io.getstream.chat.android.client.events.ChannelUpdatedByUserEvent
import io.getstream.chat.android.client.events.NewMessageEvent
import io.getstream.chat.android.models.Message
import io.getstream.chat.android.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameViewModel @AssistedInject constructor(
    private val gameRepository: GameRepository,
    private val chatClient: ChatClient,
    @Assisted val cid: String
) : ViewModel() {

    private val channelClient = chatClient.channel(cid)

    private val firebaseDb = FirebaseDatabase.getInstance().getReference(channelClient.groupId)


    private val _isHost = MutableStateFlow(false)
    val isHost: StateFlow<Boolean>
        get() = _isHost

    private val _randomWords = MutableStateFlow<List<String>?>(null)
    val randomWords: StateFlow<List<String>?>
        get() = _randomWords


    private val _gameChatMessages = MutableStateFlow<List<GameChatMessage>>(listOf())
    val gameChatMessages: StateFlow<List<GameChatMessage>>
        get() = _gameChatMessages

    private val _selectedWord = MutableStateFlow<String?>(null)
    val selectedWord: StateFlow<String?> = _selectedWord

    private val _newDrawingImage: MutableState<String?> = mutableStateOf(null)
    val newDrawingImage: State<String?> = _newDrawingImage

    init {
        fetchChannelInformation()
        subscribeToChannelEvents()
        subscribeToNewMessageEvent()
    }

    fun sendGuessToChannel(guess: String) = viewModelScope.launch {
        channelClient.sendMessage(
            Message(user = chatClient.getCurrentUser()!!, text = guess)
        ).await()
    }

    fun setSelectedWord(word: String) = viewModelScope.launch {
        val hostName = chatClient.getCurrentUser()?.name ?: return@launch
        channelClient.update(
            extraData = mutableMapOf(
                KEY_SELECTED_WORD to word,
                KEY_NAME to hostName.groupName,
                KEY_HOST_NAME to hostName,
            )
        ).await()
    }

    fun broadcastBitmap(bitmap: Bitmap) = viewModelScope.launch {
        val stringBitmap = bitmap.toBase64String()
        firebaseDb.setValue(stringBitmap)
    }

    private fun fetchChannelInformation() = viewModelScope.launch {
        val result = channelClient.watch().await()
        result.onSuccess {
            _isHost.value = it.hostName == chatClient.getCurrentUser()?.name
            if (isHost.value) {
                getRandomWords()
            } else {
                subscribeToFirebaseDb()
            }
        }
    }

    private fun subscribeToFirebaseDb() {
        firebaseDb.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val stringBitmap = snapshot.getValue(String::class.java)
                    _newDrawingImage.value = stringBitmap
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun subscribeToChannelEvents() {
        channelClient.subscribeFor<ChannelUpdatedByUserEvent> { it ->
            val channel = it.channel
            _selectedWord.value = channel.selectedWord
        }
    }

    private fun subscribeToNewMessageEvent() {
        channelClient.subscribeFor<NewMessageEvent> {
            val receivedMessage = it.message.text.trim().lowercase()
            val targetWord = selectedWord.value?.trim()?.lowercase()
            Log.e("GameViewModel", "receivedMessage: ${receivedMessage}")
            Log.e("GameViewModel", "targetWord: ${targetWord}")

            _gameChatMessages.value = _gameChatMessages.value + GameChatMessage(
                it.user.name,
                it.message.text
            )

            if (receivedMessage == targetWord) {
                Log.e("GameViewModel", "New Message Received: ${it.message.text}")
                finishGame(it.user)
            }
        }
    }

    private fun finishGame(user: User) = viewModelScope.launch {
        Log.e("GameViewModel", "finishGame called for user: ${user.name}")
        channelClient.sendMessage(
            Message(user = chatClient.getCurrentUser()!!, text = "Congratulation! ${user.name} has correct the answer. \uD83C\uDF89")
        ).await()
    }

    private fun getRandomWords() = viewModelScope.launch {
        val randomWords = gameRepository.getRandomWords()
        _randomWords.emit(randomWords)
    }


    @AssistedFactory
    interface GameAssistedFactory {
        fun create(cid: String): GameViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideGameAssistedFactory(
            assistedFactory: GameAssistedFactory,
            cid: String
        ): ViewModelProvider.NewInstanceFactory {
            return object : ViewModelProvider.NewInstanceFactory() {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return assistedFactory.create(cid) as T
                }
            }
        }
    }
}