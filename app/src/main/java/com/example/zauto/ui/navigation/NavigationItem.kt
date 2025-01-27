package com.example.zauto.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector? = null,
    val screen: Screen
)