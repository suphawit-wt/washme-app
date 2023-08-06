package com.example.washmeapp.ui.screen.profile

sealed class ProfileEventResult {
    object Success: ProfileEventResult()
    object Unauthorized: ProfileEventResult()
    object ConflictError: ProfileEventResult()
    object UnknownError: ProfileEventResult()
}