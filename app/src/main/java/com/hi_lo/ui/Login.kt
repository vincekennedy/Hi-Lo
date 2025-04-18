package com.hi_lo.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hi_lo.viewmodel.LoginNavigationEvent
import com.hi_lo.viewmodel.SessionViewModel

@Composable
fun Login(navController: NavController, viewModel: SessionViewModel) {

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
    uiState.value.errorMessage?.let { errorMessage ->
        Toast.makeText(
            LocalContext.current,
            errorMessage,
            Toast.LENGTH_SHORT
        ).show()
        viewModel.clearError() // Clear the error after showing the toast
    }

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hi-Lo",
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = uiState.value.username,
            label = { Text("Email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email,
                capitalization = KeyboardCapitalization.None
            ),
            onValueChange = viewModel::onUsernameChange,
        )
        OutlinedTextField(
            value = uiState.value.password,
            label = { Text("Password") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Go,
                keyboardType = KeyboardType.Password,
            ),
            onValueChange = viewModel::onPasswordChange,
        )
        Spacer(modifier = Modifier.height(20.dp))
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