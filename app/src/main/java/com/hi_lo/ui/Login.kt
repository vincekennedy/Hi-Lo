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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hi_lo.data.MatchScreen
import com.hi_lo.data.viewmodel.LoginNavigationEvent
import com.hi_lo.data.viewmodel.LoginViewModel

@Composable
fun Login(navController: NavController) {

    val viewModel: LoginViewModel = viewModel()
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is LoginNavigationEvent.NavigateToCourseSelect -> {
                    navController.navigate(MatchScreen.COURSE_SELECT.name)
                }
            }
        }
    }

    Column(modifier = Modifier.padding(8.dp)) {
        Text("Login")
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = uiState.value.username,
            label = { Text("Email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            onValueChange = viewModel::onUsernameChange,
        )
        OutlinedTextField(
            value = uiState.value.password,
            label = { Text("Password") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            onValueChange = viewModel::onPasswordChange,
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            onClick = viewModel::login
        ) {
            Text(text = "Login")
        }
    }
}
