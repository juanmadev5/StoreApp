package com.jmdev.app.store.presentation.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jmdev.app.store.presentation.ui.screens.cart.CartItemsScreen
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
                    navDetail = {
                        navController.navigate(
                            NavRoutes.Detail.route.replace(
                                "{id}",
                                it.toString()
                            )
                        ) {
                            launchSingleTop = true
                        }
                    },
                    navToCart = {
                        navController.navigate(NavRoutes.Cart.route) {
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable(
                route = NavRoutes.Detail.route,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) {
                val productId = it.arguments?.getInt("id") ?: 0
                DetailScreen(
                    productId = productId,
                    navBack = {
                        navController.popBackStack()
                    },
                    navToCart = {
                        navController.navigate(NavRoutes.Cart.route) {
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable(route = NavRoutes.Cart.route) {
                CartItemsScreen(navBack = {
                    navController.popBackStack()
                }, navToDetail = {
                    navController.navigate(
                        NavRoutes.Detail.route.replace(
                            "{id}",
                            it.toString()
                        )
                    ) {
                        launchSingleTop = true
                    }
                })
            }
        }
    }

}