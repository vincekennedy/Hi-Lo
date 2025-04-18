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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hi_lo.data.MatchScreen
import com.hi_lo.data.viewmodel.MatchViewModel
import com.hi_lo.data.viewmodel.SessionViewModel


@Composable
fun HiLoApp(
    modifier: Modifier = Modifier,
    matchViewModel: MatchViewModel = MatchViewModel(),
    navController: NavHostController = rememberNavController(),
) {
    val scaffoldState = rememberScaffoldState()
    val title: String by matchViewModel.title.observeAsState("")
    val sessionViewModel: SessionViewModel = viewModel()

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
                startDestination = MatchScreen.LOGIN.name,
                modifier = modifier.padding(padding)
            ) {
                composable(route = MatchScreen.LOGIN.name) {
                    Login(navController, sessionViewModel)
                }
                composable(route = MatchScreen.COURSE_SELECT.name) {
                    CourseSelection {
                        matchViewModel.setupMatch()
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
