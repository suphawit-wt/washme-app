package com.example.washmeapp.data

import com.example.washmeapp.model.ApiResponse

interface ApiBackendInterface {
    suspend fun register(username: String?, email: String?, fullname: String?, password: String?): ApiResponse
    suspend fun login(username: String?, password: String?): ApiResponse
    suspend fun authenticate(token: String): ApiResponse
    suspend fun getUserInfo(token: String): ApiResponse
    suspend fun updateUser(token: String, username: String?, email: String?, fullname: String?, password: String?): ApiResponse
}