package com.example.gameapp.presentation.details_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gameapp.presentation.common.DataPointComponent
import com.example.gameapp.presentation.common.GameImage
import com.example.gameapp.presentation.common.GameNameComponent
import com.example.gameapp.presentation.common.LoadingState


@Composable
fun GameDetailsScreen(
    gameId: Int,
    viewModel: GameDetailsViewModel = hiltViewModel(),
) {

    LaunchedEffect(key1 = Unit, block = {
        viewModel.fetchGame(gameId)
    })

    val state by viewModel.stateFlow.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(all = 16.dp)
    ) {

        when (val viewState = state) {
            GameDetailsViewState.Loading -> item { LoadingState() }
            is GameDetailsViewState.Error -> {
                // TODO: add retry button
            }

            is GameDetailsViewState.Success -> {
                // Name plate
                item {
                    GameNameComponent(
                        name = viewState.gameDetails.name,
                    )
                }

                item { Spacer(modifier = Modifier.height(8.dp)) }

                // Image
                item {
                    GameImage(imageUrl = viewState.gameDetails.imageUrl)
                }

                // Data points
                items(viewState.gameDataPoints) {
                    Spacer(modifier = Modifier.height(32.dp))
                    DataPointComponent(dataPoint = it)
                }


                item { Spacer(modifier = Modifier.height(64.dp)) }
            }
        }
    }
}