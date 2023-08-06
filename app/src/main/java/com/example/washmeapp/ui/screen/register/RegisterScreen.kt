package com.example.washmeapp.ui.screen.register

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.washmeapp.ui.components.AppLogoImage
import com.example.washmeapp.util.ScreenRoute

@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        viewModel.registerEventResult.collect { result ->
            when (result) {
                is RegisterEventResult.Success -> {
                    Toast.makeText(
                        context, "Register Successfully!", Toast.LENGTH_LONG
                    ).show()
                    navController.navigate(route = ScreenRoute.Login)
                }
                is RegisterEventResult.ConflictError -> {
                    Toast.makeText(
                        context, "This username or email is already in use.", Toast.LENGTH_LONG
                    ).show()
                }
                is RegisterEventResult.UnknownError -> {
                    Toast.makeText(
                        context, "Internal Server Error.", Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    var passwordVisible: Boolean by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppLogoImage(width = 150.dp)
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Username")
            },
            singleLine = true,
            isError = state.usernameError != null,
            value = state.username!!,
            onValueChange = {
                viewModel.onEvent(RegisterEvent.UsernameChanged(it))
            })
        if (state.usernameError != null) {
            Text(
                text = state.usernameError,
                color = Color.Red,
                modifier = Modifier.align(Alignment.Start)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Email")
            },
            singleLine = true,
            isError = state.emailError != null,
            value = state.email!!,
            onValueChange = {
                viewModel.onEvent(RegisterEvent.EmailChanged(it))
            })
        if (state.emailError != null) {
            Text(
                text = state.emailError,
                color = Color.Red,
                modifier = Modifier.align(Alignment.Start)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Full Name")
            },
            singleLine = true,
            isError = state.fullnameError != null,
            value = state.fullname!!,
            onValueChange = {
                viewModel.onEvent(RegisterEvent.FullnameChanged(it))
            })
        if (state.fullnameError != null) {
            Text(
                text = state.fullnameError,
                color = Color.Red,
                modifier = Modifier.align(Alignment.Start)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Password")
            },
            singleLine = true,
            isError = state.passwordError != null,
            value = state.password!!,
            onValueChange = {
                viewModel.onEvent(RegisterEvent.PasswordChanged(it))
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(
                    onClick = { passwordVisible = !passwordVisible }
                ) {
                    Icon(imageVector = image, description)
                }
            })
        if (state.passwordError != null) {
            Text(
                text = state.passwordError,
                color = Color.Red,
                modifier = Modifier.align(Alignment.Start)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Confirm Password")
            },
            singleLine = true,
            isError = state.confirmPasswordError != null,
            value = state.confirmPassword!!,
            onValueChange = {
                viewModel.onEvent(RegisterEvent.ConfirmPasswordChanged(it))
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(
                    onClick = { passwordVisible = !passwordVisible }
                ) {
                    Icon(imageVector = image, description)
                }
            })
        if (state.confirmPasswordError != null) {
            Text(
                text = state.confirmPasswordError,
                color = Color.Red,
                modifier = Modifier.align(Alignment.Start)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .height(42.dp),
            onClick = {
                viewModel.onEvent(RegisterEvent.Submit)
            }
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                textAlign = TextAlign.Center,
                text = "Register",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.height(18.dp))

        Text(
            modifier = Modifier.clickable {
                navController.navigate(route = ScreenRoute.Login)
            },
            text = "Back to Login page.",
            color = Color.Blue,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }

}