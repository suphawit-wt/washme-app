package com.example.washmeapp.data

import com.example.washmeapp.model.*
import retrofit2.HttpException

class ApiBackendImpl(
    private val apiBackend: ApiBackend
): ApiBackendInterface {

    override suspend fun register(
        username: String?,
        email: String?,
        fullname: String?,
        password: String?
    ): ApiResponse {
        return try {
            apiBackend.register(
                request = Users(
                    username = username,
                    email = email,
                    fullname = fullname,
                    password = password
                )
            )
        } catch(e: HttpException) {
            if(e.code() == 409) {
                ApiResponse(statusCode = 409)
            }
            else {
                ApiResponse(statusCode = 500)
            }
        } catch (e: Exception) {
            ApiResponse(statusCode = 500)
        }
    }

    override suspend fun login(username: String?, password: String?): ApiResponse {
        return try {
            apiBackend.login(
                request = Users(
                    username = username,
                    password = password
                )
            )
        } catch(e: HttpException) {
            if(e.code() == 401) {
                ApiResponse(statusCode = 401)
            }
            else {
                ApiResponse(statusCode = 500)
            }
        } catch (e: Exception) {
            ApiResponse(statusCode = 500)
        }
    }

    override suspend fun authenticate(token: String): ApiResponse {
        return try {
            apiBackend.authenticate("Bearer $token")
        } catch(e: HttpException) {
            if(e.code() == 401) {
                ApiResponse(statusCode = 401)
            }
            else {
                ApiResponse(statusCode = 500)
            }
        } catch (e: Exception) {
            ApiResponse(statusCode = 500)
        }
    }

    override suspend fun getUserInfo(token: String): ApiResponse {
        return try {
            apiBackend.getUserInfo("Bearer $token")
        } catch(e: HttpException) {
            if(e.code() == 401) {
                ApiResponse(statusCode = 401)
            }
            else {
                ApiResponse(statusCode = 500)
            }
        } catch (e: Exception) {
            ApiResponse(statusCode = 500)
        }
    }

    override suspend fun updateUser(
        token: String,
        username: String?,
        email: String?,
        fullname: String?,
        password: String?
    ): ApiResponse {
        return try {
            apiBackend.updateUser(
                token = "Bearer $token",
                request = Users(
                    username = username,
                    email = email,
                    fullname = fullname,
                    password = password
            ))
        } catch(e: HttpException) {
            if(e.code() == 401) {
                ApiResponse(statusCode = 401)
            }
            else if(e.code() == 409) {
                ApiResponse(statusCode = 409)
            }
            else {
                ApiResponse(statusCode = 500)
            }
        } catch (e: Exception) {
            ApiResponse(statusCode = 500)
        }
    }

}