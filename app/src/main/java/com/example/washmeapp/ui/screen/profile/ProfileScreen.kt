package com.example.washmeapp.ui.screen.profile

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.washmeapp.ui.components.AppLogoImage
import com.example.washmeapp.util.ScreenRoute

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        viewModel.profileEventResult.collect { result ->
            when (result) {
                is ProfileEventResult.Success -> {
                    Toast.makeText(
                        context, "Update Profile Successfully!", Toast.LENGTH_LONG
                    ).show()
                }
                is ProfileEventResult.Unauthorized -> {
                    Toast.makeText(
                        context, "You're unauthorized.", Toast.LENGTH_LONG
                    ).show()
                }
                is ProfileEventResult.ConflictError -> {
                    Toast.makeText(
                        context, "This username or email is already in use.", Toast.LENGTH_LONG
                    ).show()
                }
                is ProfileEventResult.UnknownError -> {
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

    val selectedItem by remember { mutableStateOf(2) }
    val items = listOf("Home", "Search", "Profile", "Menu")
    val itemsIcon = listOf(
        Icons.Filled.Home,
        Icons.Filled.Search,
        Icons.Filled.Person,
        Icons.Filled.Menu,
    )
    val itemsNav = listOf(
        ScreenRoute.Home,
        ScreenRoute.Search,
        ScreenRoute.Profile,
        ScreenRoute.Menu
    )

    var passwordVisible: Boolean by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                contentColor = Color.Blue,
                title = {
                    AppLogoImage(width = 130.dp)
                }
            )
        },
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.Blue,
                contentColor = Color.White,
                content = {
                    items.forEachIndexed { index, item ->
                        BottomNavigationItem(
                            icon = { Icon(itemsIcon[index], contentDescription = null) },
                            label = {
                                Text(
                                    text = item,
                                    fontWeight = FontWeight.SemiBold
                                ) },
                            selected = selectedItem == index,
                            onClick = {
                                navController.navigate(itemsNav[index])
                            }
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(top = 18.dp, start = 18.dp, end = 18.dp)
        ) {

            Text(
                text = "Profile",
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(18.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Username")
                },
                singleLine = true,
                isError = state.usernameError != null,
                value = state.username!!,
                onValueChange = {
                    viewModel.onEvent(ProfileEvent.UsernameChanged(it))
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
                    viewModel.onEvent(ProfileEvent.EmailChanged(it))
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
                    viewModel.onEvent(ProfileEvent.FullnameChanged(it))
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
                    viewModel.onEvent(ProfileEvent.PasswordChanged(it))
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
                    viewModel.onEvent(ProfileEvent.ConfirmPasswordChanged(it))
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
                    viewModel.onEvent(ProfileEvent.Submit)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color("#EA8110".toColorInt())
                )
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    textAlign = TextAlign.Center,
                    text = "Update",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }
    }
}