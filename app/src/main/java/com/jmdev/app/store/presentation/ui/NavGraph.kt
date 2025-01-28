package com.jmdev.app.store.presentation.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jmdev.app.store.presentation.ui.screens.detail.DetailScreen
import com.jmdev.app.store.presentation.ui.screens.home.HomeScreen

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            startDestination = NavRoutes.Home.route,
            navController = navController
        ) {
            composable(route = NavRoutes.Home.route) {
                HomeScreen(
                    navDetail = {}
                )
            }
            composable(route = NavRoutes.Detail.route) {
                DetailScreen(
                    navBack = {}
                )
            }
        }
    }

}