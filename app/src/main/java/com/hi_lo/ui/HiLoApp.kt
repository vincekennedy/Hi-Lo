package com.hi_lo.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.primarySurface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hi_lo.viewmodel.MatchViewModel
import com.hi_lo.viewmodel.SessionViewModel


@Composable
fun HiLoApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    matchViewModel: MatchViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val sessionViewModel: SessionViewModel = hiltViewModel()
    val title by matchViewModel.title.collectAsState()


    LaunchedEffect(Unit) {
        val matchData = matchViewModel.loadMatchData()
        if (matchData != null) {
            navController.navigate(MatchScreen.SCORE.name)
        } else {
            navController.navigate(MatchScreen.COURSE_SELECT.name)
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                navigationIcon = null, title = {
                    Text(text = title)
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
                    Login(navController, sessionViewModel)
                }
                composable(route = MatchScreen.COURSE_SELECT.name) {
                    CourseSelection(matchViewModel) {
                        navController.navigate(MatchScreen.SETUP_MATCH.name)
                    }
                }
                composable(route = MatchScreen.SETUP_MATCH.name) {
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
