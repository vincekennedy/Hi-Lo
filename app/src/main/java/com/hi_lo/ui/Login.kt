package com.hi_lo.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hi_lo.data.MatchScreen
import com.hi_lo.data.MatchViewModel

@Composable
fun Login(navController: NavController, matchViewModel: MatchViewModel) {
    val email: MutableState<String> = remember {
        mutableStateOf("")
    }
    val password: MutableState<String> = remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.padding(8.dp)) {
        Text("Login")
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = email.value,
            label = { Text("Email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            onValueChange = {
                if (it.isNotEmpty()) {
                    email.value = it
                } else {
                    email.value = ""
                }
            },
        )
        OutlinedTextField(
            value = password.value,
            label = { Text("Password") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            onValueChange = {
                if (it.isNotEmpty()) {
                    password.value = it
                } else {
                    password.value = ""
                }
            },
        )
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
            onClick = {
                matchViewModel.login("kennedy.v@gmail.com", "test123")
                navController.navigate(MatchScreen.COURSE_SELECT.name)
            }) {
            Text(text = "Login")
        }
    }
}
