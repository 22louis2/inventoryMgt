package com.example.inventorymgmt.core.network

import com.example.inventorymgmt.feature.data.remote.api.JokeApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiProvider {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://sv443.net/jokeapi/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val jokeApiService: JokeApiService by lazy {
        retrofit.create(JokeApiService::class.java)
    }
}