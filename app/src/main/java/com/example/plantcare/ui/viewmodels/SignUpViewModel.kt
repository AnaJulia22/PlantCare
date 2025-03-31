package com.example.plantcare.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.plantcare.Authentication.FirebaseAuthRepository
import com.example.plantcare.ui.states.SignUpUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignUpViewModel(
    private val firebaseAuthRepository: FirebaseAuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()
    private val _signUpIsSuccessful = MutableSharedFlow<Boolean>()
    val signUpIsSuccessful = _signUpIsSuccessful.asSharedFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onEmailChange = { email ->
                    _uiState.update {
                        it.copy(email = email)
                    }
                },
                onPasswordChange = { password ->
                    _uiState.update {
                        it.copy(password = password)
                    }
                },
                onConfirmPasswordChange = { password ->
                    _uiState.update {
                        it.copy(confirmPassword = password)
                    }
                }
            )
        }
    }

    suspend fun signUp() {
        try {
            firebaseAuthRepository
                .signUp(
                    _uiState.value.email,
                    _uiState.value.password
                )
            _signUpIsSuccessful.emit(true)
        } catch (e: Exception) {
            Log.e("SignUpViewModel", "signUp: ",e )
            _uiState.update {
                it.copy(
                    error = "Erro ao cadastrar usuário"
                )
            }

            delay(3000)
            _uiState.update {
                it.copy(
                    error = null
                )
            }
        }
    }
}