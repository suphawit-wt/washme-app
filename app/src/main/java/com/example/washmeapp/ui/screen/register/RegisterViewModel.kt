package com.example.washmeapp.ui.screen.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.washmeapp.data.ApiBackendInterface
import com.example.washmeapp.data.ValidationInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val validate: ValidationInterface,
    private val apiBackend: ApiBackendInterface
): ViewModel() {

    var state by mutableStateOf(RegisterState())

    private val resultChannel = Channel<RegisterEventResult>()
    val registerEventResult = resultChannel.receiveAsFlow()

    fun onEvent(event: RegisterEvent) {
        when(event) {
            is RegisterEvent.UsernameChanged -> {
                state = state.copy(username = event.username)
            }
            is RegisterEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is RegisterEvent.FullnameChanged -> {
                state = state.copy(fullname = event.fullname)
            }
            is RegisterEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is RegisterEvent.ConfirmPasswordChanged -> {
                state = state.copy(confirmPassword = event.confirmPassword)
            }
            is RegisterEvent.Submit -> {
                registerSubmit()
            }
        }
    }

    private fun registerSubmit() {
        val usernameValidate = validate.username(state.username)
        val emailValidate = validate.email(state.email)
        val fullnameValidate = validate.fullname(state.fullname)
        val passwordValidate = validate.password(state.password)
        val confirmPasswordValidate = validate.confirmPassword(state.password, state.confirmPassword)

        val hasError = listOf(
            usernameValidate,
            emailValidate,
            fullnameValidate,
            passwordValidate,
            confirmPasswordValidate
        ).any { !it.successful }

        if(hasError) {
            state = state.copy(
                usernameError = usernameValidate.errorMessage,
                emailError = emailValidate.errorMessage,
                fullnameError = fullnameValidate.errorMessage,
                passwordError = passwordValidate.errorMessage,
                confirmPasswordError = confirmPasswordValidate.errorMessage
            )
            return
        } else {
            state = state.copy(
                usernameError = null,
                emailError = null,
                fullnameError = null,
                passwordError = null,
                confirmPasswordError = null
            )
        }

        viewModelScope.launch {
            state = state.copy(isLoading = true)

            val response = withContext(Dispatchers.IO) {
                apiBackend.register(
                    username = state.username,
                    email = state.email,
                    fullname = state.fullname,
                    password = state.password
                )
            }

            when (response.statusCode) {
                201 -> {
                    resultChannel.send(RegisterEventResult.Success)
                }
                409 -> {
                    resultChannel.send(RegisterEventResult.ConflictError)
                }
                else -> {
                    resultChannel.send(RegisterEventResult.UnknownError)
                }
            }

            state = state.copy(isLoading = false)
        }
    }
}