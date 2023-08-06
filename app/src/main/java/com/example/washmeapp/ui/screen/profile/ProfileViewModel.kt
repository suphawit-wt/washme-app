package com.example.washmeapp.ui.screen.profile

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
class ProfileViewModel @Inject constructor(
    private val validate: ValidationInterface,
    private val apiBackend: ApiBackendInterface,
    private val prefs: SharedPreferences
): ViewModel() {

    var state by mutableStateOf(ProfileState())

    private val resultChannel = Channel<ProfileEventResult>()
    val profileEventResult = resultChannel.receiveAsFlow()

    init {
        getUserInfo()
    }

    fun onEvent(event: ProfileEvent) {
        when(event) {
            is ProfileEvent.UsernameChanged -> {
                state = state.copy(username = event.username)
            }
            is ProfileEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is ProfileEvent.FullnameChanged -> {
                state = state.copy(fullname = event.fullname)
            }
            is ProfileEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is ProfileEvent.ConfirmPasswordChanged -> {
                state = state.copy(confirmPassword = event.confirmPassword)
            }
            is ProfileEvent.Submit -> {
                updateProfileSubmit()
            }
        }
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            val token = prefs.getString("jwt", null)

            if (token != null) {
                val response = withContext(Dispatchers.IO) {
                    apiBackend.getUserInfo(token = token)
                }

                when (response.statusCode) {
                    200 -> {
                        state = state.copy(
                            username = response.user!!.username,
                            email = response.user.email,
                            fullname = response.user.fullname
                        )
                    }
                    401 -> {
                        resultChannel.send(ProfileEventResult.Unauthorized)
                    }
                    else -> {
                        resultChannel.send(ProfileEventResult.UnknownError)
                    }
                }
            } else {
                resultChannel.send(ProfileEventResult.UnknownError)
            }

            state = state.copy(isLoading = false)
        }
    }

    private fun updateProfileSubmit() {
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

            val token = prefs.getString("jwt", null)

            if (token != null) {
                val response = withContext(Dispatchers.IO) {
                    apiBackend.updateUser(
                        token = token,
                        username = state.username,
                        email = state.email,
                        fullname = state.fullname,
                        password = state.password
                    )
                }

                when (response.statusCode) {
                    200 -> {
                        resultChannel.send(ProfileEventResult.Success)
                    }
                    401 -> {
                        resultChannel.send(ProfileEventResult.Unauthorized)
                    }
                    409 -> {
                        resultChannel.send(ProfileEventResult.ConflictError)
                    }
                    else -> {
                        resultChannel.send(ProfileEventResult.UnknownError)
                    }
                }
            } else {
                resultChannel.send(ProfileEventResult.UnknownError)
            }

            state = state.copy(isLoading = false)
        }
    }

}