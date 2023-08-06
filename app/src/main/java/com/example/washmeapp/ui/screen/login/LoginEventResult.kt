package com.example.washmeapp.ui.screen.login

sealed class LoginEventResult {
    object Authorized: LoginEventResult()
    object Unauthorized: LoginEventResult()
    object UnknownError: LoginEventResult()
}