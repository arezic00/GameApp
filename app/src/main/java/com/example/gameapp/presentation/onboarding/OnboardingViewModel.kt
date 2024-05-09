package com.example.gameapp.presentation.onboarding

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gameapp.data.local.GenreDatabase
import com.example.gameapp.data.repository.DataStoreRepository
import com.example.gameapp.data.repository.GameRepository
import com.example.gameapp.domain.Genre
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val repository: DataStoreRepository,
    private val gameRepository: GameRepository,
    private val database: GenreDatabase
) : ViewModel() {

    private val _viewState = MutableStateFlow<OnboardingViewState>(OnboardingViewState.Loading)
    val viewState: StateFlow<OnboardingViewState> = _viewState.asStateFlow()

    private val fetchedGenres: SnapshotStateList<Genre> = mutableStateListOf()

    fun saveOnboardingState(completed: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveOnBoardingState(completed = completed)
        }
    }

    suspend fun saveGenres(genres: List<Genre>) {
        database.genreDao().addGenres(genres)
    }

    fun fetchGenres() = viewModelScope.launch {
        fetchedGenres.clear()
        fetchedGenres.addAll(database.genreDao().getAllGenres())
        if (fetchedGenres.isEmpty()) {
            gameRepository.fetchGenres().onSuccess {
                fetchedGenres.addAll(it)
            }.onFailure {
                // TODO: add error message and error state
            }
        }

        _viewState.update { return@update OnboardingViewState.LazyGridDisplay(genres = fetchedGenres) }
    }

    fun onSelectGenre(genre: Genre) {
        val updatedGenres = fetchedGenres.map {
            if (it == genre) it.copy(isSelected = !it.isSelected)
            else it
        }
        fetchedGenres.clear()
        fetchedGenres.addAll(updatedGenres)
        _viewState.update { return@update OnboardingViewState.LazyGridDisplay(genres = fetchedGenres) }
    }

}