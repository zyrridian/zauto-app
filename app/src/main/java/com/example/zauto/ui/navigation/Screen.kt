package com.example.zauto.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Favorite : Screen("favorite")
    data object Profile : Screen("profile")
    data object CarList : Screen("carList")
    data object Detail : Screen("home/{carId}") {
        fun createRoute(carId: Int) = "home/$carId"
    }
}