package com.example.inventorymgmt.core.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.Data
import com.example.inventorymgmt.core.network.ApiProvider
import kotlinx.coroutines.runBlocking

class JokeWorker(appContext: Context, params: WorkerParameters) : Worker(appContext, params) {
    override fun doWork(): Result {
        return try {
            val joke = runBlocking {
                val response = ApiProvider.jokeApiService.getJoke()
                when (response.type) {
                    "single" -> response.joke ?: ""
                    "twopart" -> "${response.setup}\n${response.delivery}"
                    else -> ""
                }
            }
            val output = Data.Builder().putString("joke", joke).build()
            Result.success(output)
        } catch (e: Exception) {
            val error = Data.Builder().putString("error", e.message).build()
            Result.failure(error)
        }
    }
}
