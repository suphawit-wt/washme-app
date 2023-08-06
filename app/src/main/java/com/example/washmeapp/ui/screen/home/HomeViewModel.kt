package com.example.washmeapp.ui.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.washmeapp.data.ApiBackendInterface
import com.example.washmeapp.ui.screen.menu.MenuEvent
import com.example.washmeapp.ui.screen.menu.MenuEventResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiBackend: ApiBackendInterface
): ViewModel() {

    var state by mutableStateOf(HomeState())

    private val resultChannel = Channel<MenuEventResult>()
    val homeEventResult = resultChannel.receiveAsFlow()

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
            state = state.copy(isLoading = false)
        }
    }

}