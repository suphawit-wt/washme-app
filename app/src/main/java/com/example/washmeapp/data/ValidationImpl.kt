package com.example.washmeapp.data

import android.util.Patterns
import com.example.washmeapp.model.ValidationResult

class ValidationImpl(): ValidationInterface {
    override fun username(username: String?): ValidationResult {
        if(username!!.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please enter Username."
            )
        }
        return ValidationResult(
            successful = true
        )
    }

    override fun email(email: String?): ValidationResult {
        if(email!!.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please enter Email."
            )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please enter valid Email format."
            )
        }
        return ValidationResult(
            successful = true
        )
    }

    override fun fullname(fullname: String?): ValidationResult {
        if(fullname!!.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please enter Fullname."
            )
        }
        return ValidationResult(
            successful = true
        )
    }

    override fun password(password: String?): ValidationResult {
        if(password!!.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = "Password length must be at least 8 characters."
            )
        }
        val containsLettersAndDigits = password.any { it.isDigit() } &&
                password.any { it.isLetter() }
        if(!containsLettersAndDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "Password must be included at least 1 Alphabet and Number."
            )
        }
        return ValidationResult(
            successful = true
        )
    }

    override fun confirmPassword(
        password: String?,
        confirmPassword: String?
    ): ValidationResult {
        if(password != confirmPassword) {
            return ValidationResult(
                successful = false,
                errorMessage = "Password and Confirm Password must be matched."
            )
        }
        return ValidationResult(
            successful = true
        )
    }

}