package com.hi_lo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize.Min
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Checkbox
import androidx.compose.material.OutlinedTextField
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
import androidx.lifecycle.MutableLiveData
import com.hi_lo.viewmodel.Golfer
import com.hi_lo.viewmodel.HoleViewModel
import com.hi_lo.viewmodel.Score


@Composable
fun PlayerScore(
  golfer: Golfer,
  hcp: Int,
  playerScore: MutableState<Score>,
  holeScore: HoleViewModel
) {

  val strokes = if (golfer.hcp >= hcp) {
    if (golfer.hcp.minus(18).minus(hcp) > 0) {
      2
    } else {
      1
    }
  } else {
    0
  }

  val background = when (strokes) {
    1 -> {
      Color.Green
    }
    2 -> {
      Color.Yellow
    }
    else -> {
      Color.Transparent
    }
  }

  val score = remember { mutableStateOf("") }

  Row(verticalAlignment = Alignment.CenterVertically) {
    Text(
      text = "${golfer.name} (${golfer.hcp})",
      modifier = Modifier
        .background(background),
    )
    Spacer(modifier = Modifier.width(12.dp))
    OutlinedTextField(
      value = score.value,
      label = { Text("Score") },
      onValueChange = {
        if (it.isEmpty()) {
          score.value = ""
        } else if (it.isDigitsOnly() && it.toInt() <= 9) {
          score.value = it
          playerScore.value.strokes = it.toInt().minus(strokes)
        }
      },
      keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Number,
        imeAction = ImeAction.Next
      )
    )
  }
  PointEntry(playerScore, holeScore)
}

@Composable
fun PointEntry(playerScore: MutableState<Score>, holeScore: HoleViewModel) {
  Row {
    LabeledCheckbox(label = "First", playerScore, holeScore.firstOnGreen)
    LabeledCheckbox(label = "Closest", playerScore, holeScore.closestToThePin)
    LabeledCheckbox(label = "Sneaky", playerScore)
    LabeledCheckbox(label = "Pully", playerScore)
    LabeledCheckbox(label = "Sandy", playerScore)

  }
}

@Composable
fun LabeledCheckbox(
  label: String,
  playerScore: MutableState<Score>,
  winningPlayerNumber: MutableLiveData<Int>? = null
) {
  val isChecked = remember { mutableStateOf(false) }

  val isEnabled = remember {
    mutableStateOf(
      winningPlayerNumber?.value == 0 || playerScore.value.playerNumber == winningPlayerNumber?.value || winningPlayerNumber == null
    )
  }

  Column(
    modifier = Modifier
      .width(Min)
      .padding(4.dp)
  ) {
    Text(text = label)
    Checkbox(checked = isChecked.value,
             enabled = isEnabled.value,
             onCheckedChange = {
               if (winningPlayerNumber?.value == 0 || winningPlayerNumber?.value == playerScore.value.playerNumber){
                 if (it) {
                   playerScore.value.points++
                   winningPlayerNumber.value = playerScore.value.playerNumber
                 } else {
                   playerScore.value.points--
                   winningPlayerNumber.value = 0
                 }
                 isChecked.value = it
               }

               if (winningPlayerNumber == null) {
                 if (it) {
                   playerScore.value.points++
                 } else {
                   playerScore.value.points--
                 }
                 isChecked.value = it
               }
             })
  }
}


