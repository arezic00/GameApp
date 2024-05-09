package com.example.gameapp.presentation.games_screen

import com.example.gameapp.domain.Game

sealed interface GamesScreenViewState {
    data object Loading : GamesScreenViewState
    data class LazyColumnDisplay(
        val games: List<Game> = emptyList()
    ) : GamesScreenViewState
}