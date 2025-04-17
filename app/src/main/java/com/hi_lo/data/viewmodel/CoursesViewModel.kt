package com.hi_lo.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hi_lo.data.Course
import com.hi_lo.data.retrofit.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class CoursesViewModel(
    private val sharedSessionViewModel: SessionViewModel
) : ViewModel() {
    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses

    fun fetchCourses() {
        val token = sharedSessionViewModel.getSessionToken()
        if (token != null) {
            viewModelScope.launch {
                try {
                    val fetchedCourses = ApiClient.courseService.getCourses(token)
                    _courses.value = fetchedCourses
                } catch (e: Exception) {
                    Timber.e("Something went wrong.", e)
                }
            }
        }
    }
}