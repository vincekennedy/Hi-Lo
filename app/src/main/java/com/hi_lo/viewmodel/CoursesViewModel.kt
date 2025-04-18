package com.hi_lo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hi_lo.Course
import com.hi_lo.SessionManager
import com.hi_lo.network.CourseService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CoursesUiState(
    val isLoading: Boolean = false,
    val courses: List<Course> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class CoursesViewModel @Inject constructor(
    private val courseService: CourseService,
    private val sessionManager: SessionManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(CoursesUiState())
    val uiState: StateFlow<CoursesUiState> = _uiState

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses


    fun fetchCourses() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val token = sessionManager.getSessionToken() ?: throw IllegalStateException("Token is null")
                val courses = courseService.getCourses(token)
                _uiState.value = _uiState.value.copy(isLoading = false, courses = courses)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message)
            }
        }
    }
}