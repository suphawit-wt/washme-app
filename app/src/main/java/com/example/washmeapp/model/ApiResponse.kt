package com.example.washmeapp.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class ApiResponse(
    val statusCode: Int,
    val token: String? = null,
    val user: Users? = null,

    @Transient
    val error: Exception? = null
)
