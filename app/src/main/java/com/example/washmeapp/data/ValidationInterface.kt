package com.example.washmeapp.data

import com.example.washmeapp.model.ValidationResult

interface ValidationInterface {
    fun username(username: String?): ValidationResult
    fun email(email: String?): ValidationResult
    fun fullname(fullname: String?): ValidationResult
    fun password(password: String?): ValidationResult
    fun confirmPassword(password: String?, confirmPassword: String?): ValidationResult
}