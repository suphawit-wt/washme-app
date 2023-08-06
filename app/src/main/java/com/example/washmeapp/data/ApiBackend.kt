package com.example.washmeapp.data

import com.example.washmeapp.model.*
import retrofit2.http.*

interface ApiBackend {

    @POST("/api/register")
    suspend fun register(
        @Body request: Users
    ): ApiResponse

    @POST("/api/login")
    suspend fun login(
        @Body request: Users
    ): ApiResponse

    @GET("/api/user/info")
    suspend fun getUserInfo(
        @Header("Authorization") token: String
    ): ApiResponse

    @PUT("/api/user/update")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Body request: Users
    ): ApiResponse

    @GET("/api/authenticate")
    suspend fun authenticate(
        @Header("Authorization") token: String
    ): ApiResponse
}