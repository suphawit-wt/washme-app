package com.example.washmeapp.ui.screen.login

import android.content.SharedPreferences
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
class LoginViewModel @Inject constructor(
    private val validate: ValidationInterface,
    private val apiBackend: ApiBackendInterface,
    private val prefs: SharedPreferences
): ViewModel() {

    var state by mutableStateOf(LoginState())

    private val resultChannel = Channel<LoginEventResult>()
    val loginEventResult = resultChannel.receiveAsFlow()

    init {
        authenticate()
    }

    fun onEvent(event: LoginEvent) {
        when(event) {
            is LoginEvent.UsernameChanged -> {
                state = state.copy(username = event.username)
            }
            is LoginEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is LoginEvent.Submit -> {
                loginSubmit()
            }
        }
    }

    private fun loginSubmit() {
        val usernameValidate = validate.username(state.username)
        val passwordValidate = validate.password(state.password)

        val hasError = listOf(
            usernameValidate,
            passwordValidate
        ).any { !it.successful }

        if(hasError) {
            state = state.copy(
                usernameError = usernameValidate.errorMessage,
                passwordError = passwordValidate.errorMessage
            )
            return
        } else {
            state = state.copy(
                usernameError = null,
                passwordError = null
            )
        }

        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val response = withContext(Dispatchers.IO) {
                apiBackend.login(
                    username = state.username,
                    password = state.password
                )
            }

            when (response.statusCode) {
                200 -> {
                    prefs.edit()
                        .putString("jwt", response.token)
                        .apply()
                    resultChannel.send(LoginEventResult.Authorized)
                }
                401 -> {
                    resultChannel.send(LoginEventResult.Unauthorized)
                }
                else -> {
                    resultChannel.send(LoginEventResult.UnknownError)
                }
            }
            state = state.copy(isLoading = false)
        }
    }

    private fun authenticate() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            val token = prefs.getString("jwt", null)
            
            if (token != null) {
                val response = withContext(Dispatchers.IO) {
                    apiBackend.authenticate(token = token)
                }

                when (response.statusCode) {
                    200 -> {
                        resultChannel.send(LoginEventResult.Authorized)
                    }
                    401 -> {
                        resultChannel.send(LoginEventResult.Unauthorized)
                    }
                    else -> {
                        resultChannel.send(LoginEventResult.UnknownError)
                    }
                }
            } else {
                resultChannel.send(LoginEventResult.Unauthorized)
            }

            state = state.copy(isLoading = false)
        }
    }

}