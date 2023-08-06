package com.example.washmeapp.ui.screen.profile

sealed class ProfileEvent {
    data class UsernameChanged(val username: String): ProfileEvent()
    data class EmailChanged(val email: String): ProfileEvent()
    data class FullnameChanged(val fullname: String): ProfileEvent()
    data class PasswordChanged(val password: String): ProfileEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String): ProfileEvent()
    object Submit: ProfileEvent()
}