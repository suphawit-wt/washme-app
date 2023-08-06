package com.example.washmeapp.ui.screen.login

data class LoginState(
    val isLoading: Boolean = false,
    val username: String? = "",
    val usernameError: String? = null,
    val password: String? = "",
    val passwordError: String? = null,
)