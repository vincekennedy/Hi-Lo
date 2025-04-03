package com.hi_lo.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
  name: MutableState<String>,
  handicap: MutableState<String>
) {
  Row(modifier = Modifier.padding(4.dp)) {
    NameEntry(name)
    Spacer(modifier = Modifier.width(12.dp))
    HandicapEntry(handicap)
  }
}

@Composable
private fun NameEntry(name: MutableState<String>) {
  var nameError by remember { mutableStateOf(false) }

  OutlinedTextField(
    value = name.value,
    label = { Text("Name") },
    singleLine = true,
    isError = nameError,
    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    onValueChange = {
      if (it.isNotEmpty()) {
        name.value = it
      } else {
        nameError = true
        name.value = ""
      }
    },
  )
}

@Composable
private fun HandicapEntry(handicap: MutableState<String>) {
  OutlinedTextField(
    value = handicap.value,
    label = { Text("Index") },
    singleLine = true,
    onValueChange = {
      if (it.isEmpty()) {
        handicap.value = ""
      }
      if (it.isNotEmpty() && it.isDigitsOnly() && it.toInt() <= 36) {
        handicap.value = it
      }
    },
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
  )
}
