package com.example.washmeapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.washmeapp.util.ScreenRoute
import com.example.washmeapp.ui.screen.home.HomeScreen
import com.example.washmeapp.ui.screen.login.LoginScreen
import com.example.washmeapp.ui.screen.menu.MenuScreen
import com.example.washmeapp.ui.screen.profile.ProfileScreen
import com.example.washmeapp.ui.screen.register.RegisterScreen

@Composable
fun SetupNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = ScreenRoute.Root,
        startDestination = ScreenRoute.Login
    ) {
        composable(route = ScreenRoute.Login){
            LoginScreen(navController = navController)
        }
        composable(route = ScreenRoute.Register) {
            RegisterScreen(navController = navController)
        }
        composable(route = ScreenRoute.Home) {
            HomeScreen(navController = navController)
        }
        composable(route = ScreenRoute.Profile) {
            ProfileScreen(navController = navController)
        }
        composable(route = ScreenRoute.Menu) {
            MenuScreen(navController = navController)
        }
    }

}