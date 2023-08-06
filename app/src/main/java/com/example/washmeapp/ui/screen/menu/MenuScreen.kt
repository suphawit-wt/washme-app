package com.example.washmeapp.ui.screen.menu

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.washmeapp.ui.components.AppLogoImage
import com.example.washmeapp.util.ScreenRoute

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MenuScreen(
    navController: NavHostController,
    viewModel: MenuViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        viewModel.menuEventResult.collect { result ->
            when (result) {
                is MenuEventResult.Logout -> {
                    Toast.makeText(
                        context, "Logout Successfully!", Toast.LENGTH_LONG
                    ).show()
                    navController.navigate(ScreenRoute.Login)
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

    val selectedItem by remember { mutableStateOf(3) }
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
        Column() {

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
            ) {
                Text(
                    text = "Reserve History",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                onClick = {
                    viewModel.onEvent(MenuEvent.Logout)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
            ) {
                Text(
                    text = "Logout",
                    color = Color.Red,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

        }
    }

}