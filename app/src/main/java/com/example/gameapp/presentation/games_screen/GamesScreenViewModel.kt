package com.example.gameapp.presentation.games_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gameapp.data.local.GenreDatabase
import com.example.gameapp.data.repository.GameRepository
import com.example.gameapp.domain.GamePage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesScreenViewModel @Inject constructor(
    private val gameRepository: GameRepository,
    private val database: GenreDatabase
) : ViewModel() {
    private val _viewState = MutableStateFlow<GamesScreenViewState>(GamesScreenViewState.Loading)
    val viewState: StateFlow<GamesScreenViewState> = _viewState.asStateFlow()

    private val fetchedGamePages = mutableListOf<GamePage>()

    private var selectedGenresString: String? = null

    fun fetchInitialPage() = viewModelScope.launch {
        val selectedGenres = database.genreDao().getSelectedGenres()
        if (selectedGenres.isNotEmpty())
            selectedGenresString = selectedGenres.joinToString(",") { it.id.toString() }
        if (fetchedGamePages.isNotEmpty()) return@launch
        val initialPage = gameRepository.fetchGamePage(page = 1, selectedGenresString)
        initialPage.onSuccess { gamePage ->
            fetchedGamePages.clear()
            fetchedGamePages.add(gamePage)

            _viewState.update { return@update GamesScreenViewState.LazyColumnDisplay(games = gamePage.games) }
        }.onFailure {
            //todo
        }
    }

    fun fetchNextPage() = viewModelScope.launch {
        val nextPageIndex = fetchedGamePages.size + 1
        gameRepository.fetchGamePage(page = nextPageIndex, selectedGenresString)
            .onSuccess { gamePage ->
                fetchedGamePages.add(gamePage)
                _viewState.update { currentState ->
                    val currentGames =
                        (currentState as? GamesScreenViewState.LazyColumnDisplay)?.games
                            ?: emptyList()
                    return@update GamesScreenViewState.LazyColumnDisplay(games = currentGames + gamePage.games)
                }
            }.onFailure {
            //todo
        }

    }

}