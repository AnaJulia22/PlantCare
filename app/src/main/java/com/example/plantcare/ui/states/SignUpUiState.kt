package com.example.plantcare.ui.states

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val onEmailChange: (String) -> Unit = {},
    val onPasswordChange: (String) -> Unit = {},
    val onConfirmPasswordChange: (String) -> Unit = {},
    val isShowPassword: Boolean = false,
    val onTogglePasswordVisibility: () -> Unit = {},
    val error: String? = null,
)