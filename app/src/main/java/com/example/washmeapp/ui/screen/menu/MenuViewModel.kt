package com.example.washmeapp.ui.screen.menu

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val prefs: SharedPreferences
): ViewModel() {

    var state by mutableStateOf(MenuState())

    private val resultChannel = Channel<MenuEventResult>()
    val menuEventResult = resultChannel.receiveAsFlow()

    fun onEvent(event: MenuEvent) {
        when(event) {
            is MenuEvent.Logout -> {
                logout()
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            prefs.edit()
                .putString("jwt", null)
                .apply()
            resultChannel.send(MenuEventResult.Logout)
            state = state.copy(isLoading = false)
        }
    }

}