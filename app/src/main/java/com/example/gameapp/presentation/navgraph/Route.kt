package com.example.gameapp.presentation.navgraph

sealed class Route(val route: String) {
    data object Onboarding : Route(route = "onboarding_screen")
    data object GameScreen : Route(route = "game_screen")
    data object GameDetails : Route(route = "details_screen")
}