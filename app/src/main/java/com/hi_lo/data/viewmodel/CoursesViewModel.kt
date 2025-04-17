package com.hi_lo.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hi_lo.data.Course
import com.hi_lo.data.retrofit.ApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CoursesViewModel @Inject constructor() : ViewModel() {
    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses

    fun fetchCourses(token: String) {
        viewModelScope.launch {
            try {
                Timber.e("Fetching")
                val fetchedCourses = ApiClient.courseService.getCourses(token)
                _courses.value = fetchedCourses
                Timber.e("Courses fetched")
            } catch (e: Exception) {
                Timber.e("Something went wrong.", e)
            }
        }
    }
}