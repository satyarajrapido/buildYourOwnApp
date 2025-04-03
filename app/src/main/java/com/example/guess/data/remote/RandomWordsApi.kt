package com.example.guess.data.remote

import retrofit2.http.GET

interface RandomWordsApi {

    @GET("skribble_words.json")
    suspend fun getRandomWords(): List<String>
}