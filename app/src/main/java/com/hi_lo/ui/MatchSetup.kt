package com.hi_lo.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.hi_lo.data.Golfer
import com.hi_lo.data.MatchScreen.SCORE
import com.hi_lo.data.MatchViewModel
import com.hi_lo.data.Team

@Composable
fun SetupMatch(viewModel: MatchViewModel, navController: NavHostController) {
    val name1 = remember { mutableStateOf("") }
    val hcp1 = remember { mutableStateOf("") }
    val name2 = remember { mutableStateOf("") }
    val hcp2 = remember { mutableStateOf("") }
    val name3 = remember { mutableStateOf("") }
    val hcp3 = remember { mutableStateOf("") }
    val name4 = remember { mutableStateOf("") }
    val hcp4 = remember { mutableStateOf("") }

    val enableStart =
        name1.value.isNotEmpty() && name2.value.isNotEmpty()
                && name3.value.isNotEmpty() && name4.value.isNotEmpty()
                && hcp1.value.isNotEmpty() && hcp2.value.isNotEmpty()
                && hcp3.value.isNotEmpty() && hcp4.value.isNotEmpty()

    val pricePerPoint = remember { mutableStateOf("") }
    val courseHandicap = remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(8.dp)) {
        Row {
            Text(
                text = "Use Course Handicap",
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.CenterVertically)
                    .padding(end = 6.dp)
            )
            Switch(
                checked = courseHandicap.value,
                onCheckedChange = {
                    courseHandicap.value = it
                })
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text("Team 1")
        EnterGolfer(name = name1, handicap = hcp1)
        EnterGolfer(name = name2, handicap = hcp2)
        Spacer(modifier = Modifier.height(12.dp))
        Divider(
            color = Color.DarkGray, modifier = Modifier.height(1.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text("Team 2")
        EnterGolfer(name = name3, handicap = hcp3)
        EnterGolfer(name = name4, handicap = hcp4)
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
                    viewModel.pricePerPoint.value = it.toInt()
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
                viewModel.startMatch(
                    courseHandicap.value,
                    name1.value,
                    hcp1.value.toInt(),
                    name2.value,
                    hcp2.value.toInt(),
                    Team(
                        Golfer(name3.value, hcp3.value.toInt()),
                        Golfer(name4.value, hcp4.value.toInt())
                    )
                )
                navController.navigate(SCORE.name)
            },
            enabled = enableStart
        ) {
            Text(text = "Start Match")
        }
    }
}