package com.example.washmeapp.ui.screen.profile

data class ProfileState(
    val isLoading: Boolean = false,
    val username: String? = "",
    val usernameError: String? = null,
    val email: String? = "",
    val emailError: String? = null,
    val fullname: String? = "",
    val fullnameError: String? = null,
    val password: String? = "",
    val passwordError: String? = null,
    val confirmPassword: String? = "",
    val confirmPasswordError: String? = null
)