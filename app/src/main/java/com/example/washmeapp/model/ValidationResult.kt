package com.example.washmeapp.model

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
