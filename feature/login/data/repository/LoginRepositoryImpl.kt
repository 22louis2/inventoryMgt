package com.example.inventorymgmt.feature.login.data.repository

import com.example.inventorymgmt.feature.login.data.local.PreferencesDataSource
import com.example.inventorymgmt.feature.login.domain.model.UserCredentials
import com.example.inventorymgmt.feature.login.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow

class LoginRepositoryImpl( private val preferences: PreferencesDataSource
): LoginRepository {
    override suspend fun saveUserCredentials(userCredentials: UserCredentials) = preferences.saveUserCredentials(userCredentials)

    override fun getUserCredentials(): Flow<UserCredentials?> = preferences.getUserCredentials()
}