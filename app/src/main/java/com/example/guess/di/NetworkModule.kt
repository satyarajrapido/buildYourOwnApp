package com.example.guess.di

import com.example.guess.data.remote.RandomWordsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL =
        "https://gist.githubusercontent.com/skydoves/b7a045f42e66a7a61fd850e566993c9d/raw/c671a08e5bad0296e30c182ace5113bf4f18bc71/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideRandomWordsApi(retrofit: Retrofit): RandomWordsApi =
        retrofit.create(RandomWordsApi::class.java)
}