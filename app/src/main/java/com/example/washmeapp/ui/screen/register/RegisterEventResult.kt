package com.example.washmeapp.ui.screen.register

sealed class RegisterEventResult {
    object Success: RegisterEventResult()
    object ConflictError: RegisterEventResult()
    object UnknownError: RegisterEventResult()
}