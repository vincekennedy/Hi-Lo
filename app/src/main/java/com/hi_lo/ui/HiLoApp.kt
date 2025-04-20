package com.hi_lo.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.primarySurface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hi_lo.viewmodel.MatchViewModel
import com.hi_lo.viewmodel.SessionViewModel


@Composable
fun HiLoApp(
    modifier: Modifier = Modifier,
    matchViewModel: MatchViewModel = MatchViewModel(),
    navController: NavHostController = rememberNavController(),
) {
    val scaffoldState = rememberScaffoldState()
    var currentTitle by remember { mutableStateOf("Hi-Lo") }
    val sessionViewModel: SessionViewModel = viewModel()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                navigationIcon = null, title = {
                    Text(text = currentTitle)
                }, backgroundColor = MaterialTheme.colors.primarySurface
            )
        },
        content = { padding ->
            NavHost(
                navController = navController,
                startDestination = MatchScreen.SESSION_CHECK.name,
                modifier = modifier.padding(padding)
            ) {
                composable(route = MatchScreen.SESSION_CHECK.name) {
                    SessionCheckScreen(navController, sessionViewModel)
                }
                composable(route = MatchScreen.LOGIN.name) {
                    currentTitle = "Login"
                    Login(navController, sessionViewModel)
                }
                composable(route = MatchScreen.COURSE_SELECT.name) {
                    currentTitle = "Select a Course"
                    CourseSelection {
                        navController.navigate(MatchScreen.SETUP_MATCH.name)
                    }
                }
                composable(route = MatchScreen.SETUP_MATCH.name) {
                    currentTitle = "Setup Match"
                    SetupMatch(matchViewModel, navController)
                }
                composable(route = MatchScreen.SCORE.name) {
                    EnterScore(matchViewModel, navController)
                }
                composable(route = MatchScreen.SUMMARY.name) {
                    ScoringSummary(matchViewModel, navController)
                }
            }
        })
}
