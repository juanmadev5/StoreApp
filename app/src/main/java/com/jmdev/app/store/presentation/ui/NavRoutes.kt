package com.jmdev.app.store.presentation.ui

sealed class NavRoutes(val route: String) {
    data object Home : NavRoutes("home")
    data object Detail : NavRoutes("detail")
}