package com.hi_lo.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hi_lo.Course
import com.hi_lo.viewmodel.CoursesViewModel
import com.hi_lo.viewmodel.MatchViewModel


@Composable
fun CourseSelection(
    matchViewModel: MatchViewModel = hiltViewModel(),
    coursesViewModel: CoursesViewModel = hiltViewModel(),
    onSetupClicked: () -> Unit
) {

    val uiState by coursesViewModel.uiState.collectAsState()
    val selectedCourse by matchViewModel.selectedCourse.collectAsState()

    LaunchedEffect(key1 = "onLaunch") {
        coursesViewModel.fetchCourses()
    }
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Select a Course",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        when {
            uiState.isLoading -> {
                Text("Loading courses...")
            }

            uiState.error != null -> {
                Text("Error loading courses: ${uiState.error}")
            }

            else -> {
                CourseSelectDropdown(
                    courses = uiState.courses,
                    selectedCourse = selectedCourse,
                    onCourseSelected = { matchViewModel.selectCourse(it) }
                )
            }
        }

        // Show the button only if a course is selected
        if (selectedCourse != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onSetupClicked,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Continue to Setup")
            }
        }
    }
}

@Composable
fun CourseSelectDropdown(
    courses: List<Course>,
    selectedCourse: Course?,
    onCourseSelected: (Course) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

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
                            onCourseSelected(course)
                            expanded = false
                        }
                    ) {
                        Text(text = course.name)
                    }
                }
            }
        }
    }
}
