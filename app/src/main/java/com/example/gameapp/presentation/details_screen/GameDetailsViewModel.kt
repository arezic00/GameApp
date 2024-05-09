package com.example.gameapp.presentation.details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gameapp.data.repository.GameRepository
import com.example.gameapp.presentation.common.DataPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailsViewModel @Inject constructor(
    private val gameRepository: GameRepository
) : ViewModel() {
    private val _internalStorageFlow = MutableStateFlow<GameDetailsViewState>(
        value = GameDetailsViewState.Loading
    )
    val stateFlow = _internalStorageFlow.asStateFlow()

    fun fetchGame(gameId: Int) = viewModelScope.launch {
        _internalStorageFlow.update { return@update GameDetailsViewState.Loading }
        gameRepository.fetchGameDetails(gameId).onSuccess { gameDetails ->
            val dataPoints = buildList {
                add(DataPoint("Rating", gameDetails.rating.toString()))
                add(DataPoint("Release date", gameDetails.released))
                add(DataPoint("Latest update", gameDetails.updated.replace("T", " ")))
                add(DataPoint("Genres", gameDetails.genres.joinToString(", ")))
                add(DataPoint("Developers", gameDetails.developerTeams.joinToString(", ")))
                add(DataPoint("Publishers", gameDetails.publishers.joinToString(", ")))
                add(DataPoint("Platforms", gameDetails.platforms.joinToString(", ")))
                add(DataPoint("Description", gameDetails.description))
            }
            _internalStorageFlow.update {
                return@update GameDetailsViewState.Success(
                    gameDetails = gameDetails,
                    gameDataPoints = dataPoints
                )
            }
        }.onFailure { exception ->
            _internalStorageFlow.update {
                return@update GameDetailsViewState.Error(
                    message = exception.message ?: "Unknown error occurred"
                )
            }
        }
    }
}