package com.hi_lo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.hi_lo.ui.MatchScreen.SCORE
import com.hi_lo.ui.MatchScreen.SUMMARY
import com.hi_lo.viewmodel.HoleViewModel
import com.hi_lo.viewmodel.MatchViewModel
import com.hi_lo.viewmodel.Score
import java.lang.Integer.max
import kotlin.math.min


@Composable
fun EnterScore(matchViewModel: MatchViewModel, navController: NavHostController) {
//  val hole = matchViewModel.currentHole()
  val showConfirmation = remember { mutableStateOf(false) }
  val p1 = remember { mutableStateOf(Score(1)) }
  val p2 = remember { mutableStateOf(Score(2)) }
  val p3 = remember { mutableStateOf(Score(3)) }
  val p4 = remember { mutableStateOf(Score(4)) }

  val lowTeam = remember { mutableStateOf(0) }
  val highTeam = remember { mutableStateOf(20) }
  val holeScoreViewModel = HoleViewModel()

  Column(
    modifier = Modifier.padding(12.dp),
    verticalArrangement = Arrangement.Center
  ) {

    matchViewModel.team1?.let {
//      PlayerScore(it.golfer1, hole.holeHandicap, p1, holeScoreViewModel)
//      PlayerScore(it.golfer2, hole.holeHandicap, p2, holeScoreViewModel)
    }

    Row(
      Modifier.height(20.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Divider(
        color = Color.DarkGray, modifier = Modifier
          .height(2.dp)
      )
    }

    matchViewModel.team2?.let {
//      PlayerScore(it.golfer1, hole.holeHandicap, p1, holeScoreViewModel)
//      PlayerScore(it.golfer2, hole.holeHandicap, p2, holeScoreViewModel)
    }

    Spacer(modifier = Modifier.weight(1.0f))
    Button(modifier = Modifier
      .fillMaxWidth()
      .height(48.dp),
           content = {
             if (matchViewModel.hasNextHole()) {
               Text(text = "Next Hole")
             } else {
               Text(text = "Finish")
             }
           },
           onClick = {
             showConfirmation.value = true

             val scores =
               calculateHighLowScores(
                 matchViewModel,
                 listOf(p1.value, p2.value),
                 listOf(p3.value, p4.value)
               )
             lowTeam.value = scores.first
             highTeam.value = scores.second
           })

    if (showConfirmation.value) {
      AlertDialog(
        title = { Text(text = "Confirm Scores") },
        text = {
          Column {
            Text(text = "Low Team : ${lowTeam.value}")
            Text(text = "High Team : ${highTeam.value}")
          }
        },
        onDismissRequest = {
          showConfirmation.value = false
        },
        confirmButton = {
          Button(
            onClick = {
              showConfirmation.value = false
              calculatePointsEarned(
                matchViewModel,
                listOf(
                  p1.value.points,
                  p2.value.points,
                  p3.value.points,
                  p4.value.points
                )
              )
              if (matchViewModel.hasNextHole()) {
                matchViewModel.nextHole()
                navController.navigate(SCORE.name)
              } else {
                navController.navigate(SUMMARY.name)
              }
            }
          ) {
            Text("Confirm")
          }
        },
        dismissButton = {
          Button(
            onClick = {
              showConfirmation.value = false
            }
          ) {
            Text("Cancel")
          }
        }
      )
    }
  }
}

fun calculateHighLowScores(
  matchViewModel: MatchViewModel,
  team1: List<Score>,
  team2: List<Score>,
): Pair<Int, Int> {

  var lowTeam = 0
  var highTeam = 0

  val low1 = min(team1[0].strokes, team1[1].strokes)
  val low2 = min(team2[0].strokes, team2[1].strokes)
  val high1 = max(team1[0].strokes, team1[1].strokes)
  val high2 = max(team2[0].strokes, team2[1].strokes)

  if (low1 != low2) {
    lowTeam = if (low1 < low2) {
      matchViewModel.addPointsToTeam1Score(1)
      1
    } else {
      matchViewModel.addPointsToTeam2Score(1)
      2
    }
  }

  if (high1 != high2) {
    highTeam = if (high1 < high2) {
      matchViewModel.addPointsToTeam1Score(1)
      2
    } else {
      matchViewModel.addPointsToTeam2Score(1)
      1
    }
  }



  return Pair(lowTeam, highTeam)

}

fun calculatePointsEarned(matchViewModel: MatchViewModel, playerPoints: List<Int>) {
  matchViewModel.addPointsToTeam1Score(playerPoints[0] + playerPoints[1])
  matchViewModel.addPointsToTeam2Score(playerPoints[2] + playerPoints[3])
}

