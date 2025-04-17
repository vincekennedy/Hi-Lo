package com.hi_lo.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hi_lo.data.Course
import com.hi_lo.data.retrofit.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CoursesViewModel : ViewModel() {
    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses

    fun fetchCourses() {
        viewModelScope.launch {
            val fetchedCourses = ApiClient.courseService.getCourses("7e1cc9b936b491bd3ed838fb73e595a2a61e39ea")
            _courses.value = fetchedCourses
        }
    }
}