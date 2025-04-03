package com.example.guess.domain

import com.example.guess.data.remote.RandomWordsApi
import javax.inject.Inject

class RandomWordUseCase @Inject constructor(private val randomWordsApi: RandomWordsApi) {

    suspend fun getRandomWords(): List<String> {
        val words = randomWordsApi.getRandomWords()
        return words.asSequence().shuffled().take(3).toList()
    }
 }