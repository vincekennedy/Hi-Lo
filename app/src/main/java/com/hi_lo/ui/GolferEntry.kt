package com.hi_lo.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly

@Composable
fun EnterGolfer(
  name: String,
  handicap: String,
  onNameChange: (String) -> Unit,
  onHandicapChange: (String) -> Unit
) {
  Row(modifier = Modifier.padding(4.dp)) {
    NameEntry(name = name, onNameChange = onNameChange)
    Spacer(modifier = Modifier.width(12.dp))
    HandicapEntry(handicap = handicap, onHandicapChange = onHandicapChange)
  }
}

@Composable
private fun NameEntry(name: String, onNameChange: (String) -> Unit) {
  var isError by remember { mutableStateOf(false) }

  OutlinedTextField(
    value = name,
    label = { Text("Name") },
    singleLine = true,
    isError = isError,
    onValueChange = { newName ->
      if (newName.isNotEmpty()) {
        onNameChange(newName)
        isError = false
      } else {
        isError = true
        onNameChange("")
      }
    },
    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
  )

  if (isError) {
    Text(
      text = "Name cannot be empty",
      color = MaterialTheme.colors.error,
      style = MaterialTheme.typography.caption,
      modifier = Modifier.padding(start = 4.dp)
    )
  }
}

@Composable
private fun HandicapEntry(handicap: String, onHandicapChange: (String) -> Unit) {
  var isError by remember { mutableStateOf(false) }

  OutlinedTextField(
    value = handicap,
    label = { Text("Index") },
    singleLine = true,
    isError = isError,
    onValueChange = { newHandicap ->
      when {
        newHandicap.isEmpty() -> {
          onHandicapChange("")
          isError = false
        }
        newHandicap.isDigitsOnly() && newHandicap.toInt() in 0..36 -> {
          onHandicapChange(newHandicap)
          isError = false
        }
        else -> {
          isError = true
        }
      }
    },
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
  )

  if (isError) {
    Text(
      text = "Handicap must be a number between 0 and 36",
      color = MaterialTheme.colors.error,
      style = MaterialTheme.typography.caption,
      modifier = Modifier.padding(start = 4.dp)
    )
  }
}
