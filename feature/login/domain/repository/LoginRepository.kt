package com.example.inventorymgmt.feature.login.domain.repository

import com.example.inventorymgmt.feature.login.domain.model.UserCredentials
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun saveUserCredentials(userCredentials: UserCredentials)
    fun getUserCredentials(): Flow<UserCredentials?>
}