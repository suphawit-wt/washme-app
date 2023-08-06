package com.example.washmeapp.ui.screen.register

sealed class RegisterEvent {
    data class UsernameChanged(val username: String): RegisterEvent()
    data class EmailChanged(val email: String): RegisterEvent()
    data class FullnameChanged(val fullname: String): RegisterEvent()
    data class PasswordChanged(val password: String): RegisterEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String): RegisterEvent()
    object Submit: RegisterEvent()
}