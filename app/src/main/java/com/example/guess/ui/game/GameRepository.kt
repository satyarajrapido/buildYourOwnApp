package com.example.guess.ui.game

import com.example.guess.domain.RandomWordUseCase
import javax.inject.Inject

class GameRepository @Inject constructor(
    private val randomWordUseCase: RandomWordUseCase
) {
    suspend fun getRandomWords(): List<String> {
        return randomWordUseCase.getRandomWords()
    }
}