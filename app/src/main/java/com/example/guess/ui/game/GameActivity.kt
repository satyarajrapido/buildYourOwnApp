package com.example.guess.ui.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.guess.theme.GuessTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GameActivity : ComponentActivity() {
    private val viewModel by viewModels<GameViewModel>{
        GameViewModel.provideGameAssistedFactory(gameAssistedFactory, intent.getStringExtra(CID)!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GuessTheme {
                GameCompose(viewModel = viewModel)
            }
        }
    }
    @set:Inject
    lateinit var gameAssistedFactory: GameViewModel.GameAssistedFactory

    companion object {
        private const val CID = "cid"
        fun startGame(context: Context, cid: String){
            Intent(context, GameActivity::class.java).also {
                it.putExtra(CID, cid)
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(it)
            }
        }
    }
}