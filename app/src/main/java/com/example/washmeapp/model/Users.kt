package com.example.washmeapp.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Users(
    @Contextual
    @SerialName("_id")
    val id: String? = null,
    val username: String? = null,
    val email: String? = null,
    val fullname: String? = null,
    val password: String? = null,
    val salt: String? = null,
    val isAdmin: Boolean? = false,
)
