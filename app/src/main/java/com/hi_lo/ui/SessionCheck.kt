package com.hi_lo.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.hi_lo.viewmodel.LoginNavigationEvent
import com.hi_lo.viewmodel.SessionViewModel
import timber.log.Timber

@Composable
fun SessionCheckScreen(
    navController: NavHostController,
    sessionViewModel: SessionViewModel
) {
    LaunchedEffect(Unit) {
        sessionViewModel.checkSession()
    }

    LaunchedEffect(sessionViewModel) {
        Timber.d("Collecting navigationEvent")
        sessionViewModel.navigationEvent.collect { event ->
            Timber.d("Received navigation event: $event")
            when (event) {
                is LoginNavigationEvent.NavigateToLogin -> {
                    Timber.d("Navigating to LOGIN screen")
                    navController.navigate(MatchScreen.LOGIN.name) {
                        popUpTo("session_check") { inclusive = true }
                    }
                }
                is LoginNavigationEvent.NavigateToCourseSelect -> {
                    Timber.d("Navigating to COURSE_SELECT screen")
                    navController.navigate(MatchScreen.COURSE_SELECT.name) {
                        popUpTo("session_check") { inclusive = true }
                    }
                }
            }
        }
    }
}