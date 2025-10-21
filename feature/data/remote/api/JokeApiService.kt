package com.example.inventorymgmt.feature.data.remote.api

import com.example.inventorymgmt.feature.data.remote.dto.JokeResponse
import retrofit2.http.GET

interface JokeApiService {
    @GET("joke/Any")
    suspend fun getJoke(): JokeResponse
}