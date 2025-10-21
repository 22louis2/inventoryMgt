package com.example.inventorymgmt.feature.login.data.local

import android.content.Context
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.edit
import com.example.inventorymgmt.feature.login.domain.model.UserCredentials
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class PreferencesDataSource (
    val context: Context
)
{
    suspend fun saveUserCredentials(userCredentials: UserCredentials) {
        // get the datastore object and write username and password in datastore object
        context.dataStore.edit { preferences: MutablePreferences ->
            preferences[DataStoreKeys.USER_NAME] = userCredentials.username
            preferences[DataStoreKeys.PASSWORD] = userCredentials.password
        }
    }

    fun getUserCredentials(): Flow<UserCredentials?> {
        return context
            .dataStore
            .data
            .map { preferences ->
                val username = preferences[DataStoreKeys.USER_NAME]
                val password = preferences[DataStoreKeys.PASSWORD]
                if (username != null && password != null)
                    UserCredentials(username, password)
                else
                    null
            }
            .distinctUntilChanged()
    }
}