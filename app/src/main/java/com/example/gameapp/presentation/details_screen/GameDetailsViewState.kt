package com.example.gameapp.presentation.details_screen

import com.example.gameapp.domain.GameDetails
import com.example.gameapp.presentation.common.DataPoint

sealed interface GameDetailsViewState {
    data object Loading : GameDetailsViewState
    data class Error(val message: String) : GameDetailsViewState
    data class Success(
        val gameDetails: GameDetails,
        val gameDataPoints: List<DataPoint>
    ) : GameDetailsViewState
}
