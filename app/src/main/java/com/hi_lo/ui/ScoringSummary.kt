package com.hi_lo.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.hi_lo.MatchScreen
import com.hi_lo.MatchViewModel

@Composable
fun ScoringSummary(
    matchViewModel: MatchViewModel,
    navController: NavHostController
) {
    Column(modifier = Modifier.padding(12.dp)) {
        Text(text = "Game Summary", fontSize = 28.sp)
        Spacer(Modifier.size(12.dp))
        Text(text = matchViewModel.getFinalScore(), fontSize = 24.sp)
        Spacer(Modifier.size(40.dp))
        Text(text = "Team 1 Points: ${matchViewModel.team1Score.value.toString()}", fontSize = 20.sp)
        Spacer(Modifier.size(12.dp))
        Text(text = "Team 2 Points: ${matchViewModel.team2Score.value.toString()}", fontSize = 20.sp)
        Spacer(Modifier.size(12.dp))
        Spacer(modifier = Modifier.weight(1.0f))
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
            content = { Text(text = "Start New Match") },
            onClick = {
                matchViewModel.resetMatch()
                navController.navigate(MatchScreen.SETUP_MATCH.name)
            })
    }
}