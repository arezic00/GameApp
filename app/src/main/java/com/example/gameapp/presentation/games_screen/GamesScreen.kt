package com.example.gameapp.presentation.games_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gameapp.presentation.common.LoadingState
import com.example.gameapp.ui.theme.GamePrimary
import com.example.gameapp.ui.theme.GameSurface

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GamesScreen(
    onGameSelected: (Int) -> Unit,
    onSettingsClicked: () -> Unit,
    viewModel: GamesScreenViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()

    LaunchedEffect(key1 = viewModel, block = { viewModel.fetchInitialPage() })

    val scrollState = rememberLazyListState()
    val fetchNextPage: Boolean by remember {
        derivedStateOf {
            val currentGameCount =
                (viewState as? GamesScreenViewState.LazyColumnDisplay)?.games?.size
                    ?: return@derivedStateOf false
            val lastDisplayedIndex = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: return@derivedStateOf false
            return@derivedStateOf lastDisplayedIndex >= currentGameCount - 10
        }
    }

    LaunchedEffect(key1 = fetchNextPage, block = {
        if (fetchNextPage) {
            viewModel.fetchNextPage()
        }
    })

    when (val state = viewState) {
        GamesScreenViewState.Loading -> LoadingState()
        is GamesScreenViewState.LazyColumnDisplay -> {
            Scaffold(
                floatingActionButton =
                {
                    FloatingActionButton(
                        containerColor = GameSurface,
                        contentColor = GamePrimary,
                        onClick = { onSettingsClicked() }) {
                        Icon(imageVector = Icons.Filled.Settings, contentDescription = "Settings")
                    }
                }
            ) {
                LazyColumn(
                    state = scrollState,
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(
                        count = state.games.size,
                        itemContent = { index ->
                            GameItem(
                                game = state.games[index],
                                modifier = Modifier.fillMaxWidth()
                            ) { onGameSelected(state.games[index].id) }
                        }
                    )
                }
            }
        }
    }
}