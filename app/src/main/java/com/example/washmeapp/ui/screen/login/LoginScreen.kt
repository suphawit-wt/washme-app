package com.example.washmeapp.ui.screen.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.washmeapp.util.ScreenRoute
import androidx.compose.material.Icon
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.text.style.TextAlign
import com.example.washmeapp.ui.components.AppLogoImage

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        viewModel.loginEventResult.collect { result ->
            when (result) {
                is LoginEventResult.Authorized -> {
                    Toast.makeText(
                        context, "Login Successfully!", Toast.LENGTH_LONG
                    ).show()
                    navController.navigate(route = ScreenRoute.Home) {
                        popUpTo(route = ScreenRoute.Login) {
                            inclusive = true
                        }
                    }
                }
                is LoginEventResult.Unauthorized -> {
                    Toast.makeText(
                        context, "Username or Password is invalid.", Toast.LENGTH_LONG
                    ).show()
                }
                is LoginEventResult.UnknownError -> {
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
                viewModel.onEvent(LoginEvent.UsernameChanged(it))
            })
        if (state.usernameError != null) {
            Text(
                text = state.usernameError,
                color =  Color.Red,
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
                viewModel.onEvent(LoginEvent.PasswordChanged(it))
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
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .height(42.dp),
            onClick = {
                viewModel.onEvent(LoginEvent.Submit)
            }
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                textAlign = TextAlign.Center,
                text = "Login",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.height(18.dp))

        Text(
            modifier = Modifier.clickable {
                navController.navigate(route = ScreenRoute.Register)
            },
            text = "Register",
            color = Color.Blue,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }

}