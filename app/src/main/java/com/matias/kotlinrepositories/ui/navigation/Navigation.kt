package com.matias.kotlinrepositories.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.matias.kotlinrepositories.ui.screens.details.DetailsScreen
import com.matias.kotlinrepositories.ui.screens.home.HomeScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        addHome(navController)
        addDetails(navController)
    }
}

private fun NavGraphBuilder.addHome(
    navController: NavController,
) {
    composable(route = Screen.Home.route) {
        HomeScreen(
            onDetails = { repo -> navController.navigate(Screen.Details.createRoute(repo)) }
        )
    }
}

private fun NavGraphBuilder.addDetails(
    navController: NavController,
) {
    composable(
        route = Screen.Details.route,
        arguments = listOf(
            navArgument("owner") {
                type = NavType.StringType
            },
            navArgument("name") {
                type = NavType.StringType
            },
        )
    ) {
        DetailsScreen(navigateUp = {
            navController.navigateUp()
        })
    }
}
