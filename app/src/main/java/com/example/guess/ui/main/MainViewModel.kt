package com.example.guess.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guess.data.AppPreferences
import com.example.guess.data.GameConnectionState
import com.example.guess.utils.AppUtils
import com.example.guess.utils.CHANNEL_MESSAGING
import com.example.guess.utils.KEY_HOST_NAME
import com.example.guess.utils.KEY_LIMIT_TIME
import com.example.guess.utils.KEY_LIMIT_USER
import com.example.guess.utils.KEY_NAME
import com.example.guess.utils.channelId
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.models.Channel
import io.getstream.chat.android.models.ConnectionData
import io.getstream.chat.android.models.User
import io.getstream.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(
    private val chatClient: ChatClient,
    private val prefs: AppPreferences
) : ViewModel() {

    private val userId: String
        get() = prefs.userId ?: AppUtils.generateUserId().also { prefs.userId = it }

    private val _gameConnectionState =
        MutableStateFlow<GameConnectionState>(GameConnectionState.None)
    val gameConnectionState: StateFlow<GameConnectionState>
        get() = _gameConnectionState

    val connectedChannel: Flow<Channel?> = _gameConnectionState.filterIsInstance<GameConnectionState.Success>()
        .mapNotNull { it.channel }

    private suspend fun connectUser(displayName: String): Result<ConnectionData> {
        if (chatClient.getCurrentUser() != null) {
            chatClient.disconnect(flushPersistence = true)
        }

        val user = User(
            id = userId,
            extraData = mutableMapOf(
                KEY_NAME to displayName
            )
        )

        val token = chatClient.devToken(userId)
        return chatClient.connectUser(user, token).await()
    }

    private suspend fun createChannel(
        groupId: String,
        displayName: String,
        limitUser: Int,
        limitTime: Int
    ): Result<Channel> {
        return chatClient.createChannel(
            CHANNEL_MESSAGING,
            groupId,
            listOf(userId),
            extraData = mutableMapOf(
                KEY_NAME to "$displayName's Group",
                KEY_LIMIT_USER to limitUser,
                KEY_LIMIT_TIME to limitTime,
                KEY_HOST_NAME to displayName
            )
        ).await()
    }

    fun createGameGroup(displayName: String, limitUser: Int, limitTime: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            val connection = connectUser(displayName)
            Log.e(TAG, "createGameGroup: Connection $connection" )
            if (connection.isSuccess) {
                _gameConnectionState.emit(GameConnectionState.Loading)
                val groupId = AppUtils.generateGroupId()
                val channel = createChannel(
                    groupId = groupId,
                    displayName = displayName,
                    limitUser = limitUser,
                    limitTime = limitTime
                )
                Log.e(TAG, "createGameGroup: Channel $channel" )
                if (channel.isSuccess) {
                    Log.e(TAG, "createGameGroup: ${channel.getOrNull()}" )
                    _gameConnectionState.emit(GameConnectionState.Success(channel.getOrNull() ?: Channel()))
                } else {
                    _gameConnectionState.emit(GameConnectionState.Failure(channel.errorOrNull()))
                }
            }
        }

    fun joinGameGroup(displayName: String, groupId: String) = viewModelScope.launch{
        val connection = connectUser(displayName)
        if(connection.isSuccess){
            _gameConnectionState.emit(GameConnectionState.Loading)
            val channel = chatClient.channel(groupId.channelId)
            val result = channel.addMembers(listOf(userId)).await()
            Log.e(TAG, "joinGameGroup: result ${result.getOrNull()}", )
            if(result.isSuccess){
                Log.e(TAG, "joinGameGroup: Channel ${result.getOrNull()}", )
                _gameConnectionState.emit(GameConnectionState.Success(result.getOrNull() ?: Channel()))
            }else{
                Log.e(TAG, "joinGameGroup: ERROR ${result.errorOrNull()}", )
                _gameConnectionState.emit(GameConnectionState.Failure(result.errorOrNull()))
            }
        }
    }
}