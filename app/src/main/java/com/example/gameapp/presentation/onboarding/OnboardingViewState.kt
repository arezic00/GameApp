package com.example.gameapp.presentation.onboarding

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.gameapp.domain.Genre

sealed interface OnboardingViewState {
    data object Loading : OnboardingViewState
    data class LazyGridDisplay(
        val genres: SnapshotStateList<Genre> = mutableStateListOf()
    ) : OnboardingViewState
}