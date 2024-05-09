package com.example.gameapp.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gameapp.presentation.details_screen.GameDetailsScreen
import com.example.gameapp.presentation.games_screen.GamesScreen
import com.example.gameapp.presentation.onboarding.OnboardingScreen

@Composable
fun NavGraph(startDestination: String) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = Route.Onboarding.route) {
            OnboardingScreen(
                onFinishClicked = {
                    navController.popBackStack()
                    navController.navigate(Route.GameScreen.route)
                }
            )
        }
        composable(route = Route.GameScreen.route) {
            GamesScreen(
                onGameSelected = { gameId ->
                    navController.navigate("${Route.GameDetails.route}/$gameId")
                },
                onSettingsClicked = {
                    navController.navigate(Route.Onboarding.route)
                })
        }
        composable(
            route = "${Route.GameDetails.route}/{gameId}",
            arguments = listOf(navArgument("gameId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val gameId: Int =
                backStackEntry.arguments?.getInt("gameId") ?: -1
            GameDetailsScreen(gameId = gameId)
        }
    }
}