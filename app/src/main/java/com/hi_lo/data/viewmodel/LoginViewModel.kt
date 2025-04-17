package com.hi_lo.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hi_lo.data.retrofit.ApiClient
import com.hi_lo.data.retrofit.AuthBody
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed class LoginNavigationEvent {
    object NavigateToCourseSelect : LoginNavigationEvent()
}

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    private val _navigationEvent = MutableSharedFlow<LoginNavigationEvent>()
    val navigationEvent: SharedFlow<LoginNavigationEvent> = _navigationEvent

    fun onUsernameChange(newUsername: String) {
        _uiState.value = _uiState.value.copy(username = newUsername)
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    fun login() {
        if (_uiState.value.username.isBlank() || _uiState.value.password.isBlank()) {
            _uiState.value =
                _uiState.value.copy(errorMessage = "Username and password cannot be empty")
            return
        }

        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            try {
                val response = ApiClient.courseService.login(
                    AuthBody(
                        _uiState.value.username,
                        _uiState.value.password
                    )
                )
                if (response.isSuccessful) {
                    _navigationEvent.emit(LoginNavigationEvent.NavigateToCourseSelect)
                } else {
                    _uiState.value =
                        _uiState.value.copy(errorMessage = "Invalid username or password")
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = "An error occurred")
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}