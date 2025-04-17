package com.hi_lo.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hi_lo.data.Course
import com.hi_lo.data.CoursesViewModel
import com.hi_lo.data.MatchViewModel
import timber.log.Timber


@Composable
fun CourseSelection(matchViewModel: MatchViewModel,
                    onSetupClicked: () -> Unit) {
    val coursesViewModel: CoursesViewModel = viewModel()
    val courses by coursesViewModel.courses.collectAsState(initial = emptyList())
    Timber.e("Courses : $courses")

    LaunchedEffect(key1 = "onLaunch") {
        coursesViewModel.fetchCourses()
    }
    Column(modifier = Modifier.padding(8.dp)) {
        CourseSelectDropdown(matchViewModel, courses)
        Spacer(modifier = Modifier.height(20.dp))
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
            onClick = {
                onSetupClicked()
            }) {
            Text(text = "Setup Match")
        }
    }
}

@Composable
fun CourseSelectDropdown(matchViewModel: MatchViewModel, courses: List<Course>) {
    var expanded by remember { mutableStateOf(false) }
    var selectedCourse by remember { mutableStateOf<Course?>(null) }

    Box {
        if (courses.isEmpty()) {
            Text(
                text = "Loading courses...",
                modifier = Modifier.padding(16.dp)
            )
        } else {
            Text(
                text = selectedCourse?.name ?: "Select a course",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true }
                    .padding(16.dp)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                courses.forEach { course ->
                    DropdownMenuItem(
                        onClick = {
                            selectedCourse = course
                            expanded = false
                            matchViewModel.selectedCourse = course // Update ViewModel
                        }
                    ) {
                        Text(text = course.name)
                    }
                }
            }
        }
    }
}
