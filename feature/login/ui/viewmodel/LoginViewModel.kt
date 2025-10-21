package com.example.inventorymgmt.feature.login.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventorymgmt.feature.login.domain.model.UserCredentials
import com.example.inventorymgmt.feature.login.domain.repository.LoginRepository
import com.example.inventorymgmt.feature.login.ui.state.LoginUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel (
    private val loginRepository: LoginRepository
): ViewModel() {
    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    init {
        viewModelScope.launch {
            val loginCredential: Flow<UserCredentials?> = loginRepository.getUserCredentials()
            val existingCredentials = loginCredential.firstOrNull()
            if(existingCredentials?.username == "admin" && existingCredentials?.password == "admin") {
                Log.i("LoginViewModel", "User already logged in")
                _loginUiState.update {
                    it.copy(
                        loginSuccess = true
                    )
                }
            }

        }
    }

    fun onUsernameChange(username: String) {
        _loginUiState.update {
            it.copy(username = username)
        }
    }

    fun onPasswordChange(password: String) {
        _loginUiState.update {
            it.copy(password = password)
        }
    }

    fun login(onLoginSuccess: () -> Unit) {
        viewModelScope.launch {
            val username = loginUiState.value.username
            val password = loginUiState.value.password
            if(username == "admin" && password == "admin") {
                loginRepository.saveUserCredentials(UserCredentials(username, password))
                _loginUiState.update {
                    it.copy(
                        loginSuccess = true
                    )
                }
                Log.i("LoginViewModel", "Login successful")
                onLoginSuccess()
            } else {
                Log.e("LoginViewModel", "Login failed")
            }
        }
    }
}