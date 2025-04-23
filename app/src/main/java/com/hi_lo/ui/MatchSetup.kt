package com.hi_lo.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavHostController
import com.hi_lo.ui.MatchScreen.SCORE
import com.hi_lo.viewmodel.Golfer
import com.hi_lo.viewmodel.MatchViewModel
import com.hi_lo.viewmodel.Team

@Composable
fun SetupMatch(
    matchViewModel: MatchViewModel,
    navController: NavHostController
) {
    val team1Names = remember { mutableStateOf(Pair("", "")) }
    val team1Handicaps = remember { mutableStateOf(Pair("", "")) }
    val team2Names = remember { mutableStateOf(Pair("", "")) }
    val team2Handicaps = remember { mutableStateOf(Pair("", "")) }
    val pricePerPoint = remember { mutableStateOf("") }
    val courseHandicap = remember { mutableStateOf(false) }

    val enableStart = listOf(
        team1Names.value.first,
        team1Names.value.second,
        team2Names.value.first,
        team2Names.value.second,
        team1Handicaps.value.first,
        team1Handicaps.value.second,
        team2Handicaps.value.first,
        team2Handicaps.value.second,
        pricePerPoint.value
    ).all { it.isNotEmpty() }

    Column(modifier = Modifier.padding(8.dp)) {
        HandicapSwitch(courseHandicap)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Team 1")
        TeamEntry(
            teamNames = team1Names, teamHandicaps = team1Handicaps, onNameChange = { index, name ->
                team1Names.value =
                    if (index == 0) team1Names.value.copy(first = name) else team1Names.value.copy(
                        second = name
                    )
            },
            onHandicapChange = { index, handicap ->
                team1Handicaps.value =
                    if (index == 0) team1Handicaps.value.copy(first = handicap) else team1Handicaps.value.copy(
                        second = handicap
                    )
            })

        Spacer(modifier = Modifier.height(12.dp))
        Divider(
            color = Color.DarkGray, modifier = Modifier.height(1.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text("Team 2")
        TeamEntry(
            teamNames = team2Names, teamHandicaps = team2Handicaps, onNameChange = { index, name ->
                team2Names.value =
                    if (index == 0) team2Names.value.copy(first = name) else team2Names.value.copy(
                        second = name
                    )
            },
            onHandicapChange = { index, handicap ->
                team2Handicaps.value =
                    if (index == 0) team2Handicaps.value.copy(first = handicap) else team2Handicaps.value.copy(
                        second = handicap
                    )
            })

        Spacer(modifier = Modifier.height(12.dp))
        Divider(
            color = Color.DarkGray, modifier = Modifier.height(1.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = pricePerPoint.value,
            label = { Text("$ / Point") },
            onValueChange = {
                if (it.isEmpty()) {
                    pricePerPoint.value = ""
                } else if (it.isDigitsOnly()) {
                    pricePerPoint.value = it
                    matchViewModel.pricePerPoint.value = it.toInt()
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        )
        Spacer(modifier = Modifier.weight(1.0f))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            onClick = {
                val golfer1Handicap = team1Handicaps.value.first.toIntOrNull()
                val golfer2Handicap = team1Handicaps.value.second.toIntOrNull()
                val golfer3Handicap = team2Handicaps.value.first.toIntOrNull()
                val golfer4Handicap = team2Handicaps.value.second.toIntOrNull()

                if (golfer1Handicap != null && golfer2Handicap != null && golfer3Handicap != null && golfer4Handicap != null) {
                    matchViewModel.startMatch(
                        courseHandicap.value,
                        Team(
                            Golfer(team1Names.value.first, golfer1Handicap),
                            Golfer(team1Names.value.second, golfer2Handicap)
                        ),
                        Team(
                            Golfer(team2Names.value.first, golfer3Handicap),
                            Golfer(team2Names.value.second, golfer4Handicap)
                        )
                    )
                    navController.navigate(SCORE.name)
                }
            },
            enabled = enableStart
        ) {
            Text(text = "Start Match")
        }
    }
}

@Composable
fun HandicapSwitch(courseHandicap: MutableState<Boolean>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Use Course Handicap", modifier = Modifier.weight(1f))
        Switch(
            checked = courseHandicap.value,
            onCheckedChange = { courseHandicap.value = it }
        )
    }
}

@Composable
fun TeamEntry(
    teamNames: MutableState<Pair<String, String>>,
    teamHandicaps: MutableState<Pair<String, String>>,
    onNameChange: (Int, String) -> Unit,
    onHandicapChange: (Int, String) -> Unit
) {
    Column {
        EnterGolfer(
            name = teamNames.value.first,
            handicap = teamHandicaps.value.first,
            onNameChange = { onNameChange(0, it) },
            onHandicapChange = { onHandicapChange(0, it) }
        )
        EnterGolfer(
            name = teamNames.value.second,
            handicap = teamHandicaps.value.second,
            onNameChange = { onNameChange(1, it) },
            onHandicapChange = { onHandicapChange(1, it) }
        )
    }
}