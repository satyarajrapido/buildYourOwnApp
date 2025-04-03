package com.example.guess.data

import io.getstream.chat.android.models.Channel
import io.getstream.result.Error

sealed class GameConnectionState {
    object None: GameConnectionState()
    object Loading: GameConnectionState()
    data class Success(val channel: Channel): GameConnectionState()
    data class Failure(val error: Error?): GameConnectionState()
}