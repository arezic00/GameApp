package com.example.gameapp.presentation.onboarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gameapp.domain.Genre
import com.example.gameapp.presentation.common.LoadingState
import com.example.gameapp.ui.theme.GameAction
import com.example.gameapp.ui.theme.GamePrimary
import com.example.gameapp.ui.theme.GamePrimaryMonochromatic
import com.example.gameapp.ui.theme.GameSurface
import com.example.gameapp.ui.theme.GameTextSecondary
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    onFinishClicked: () -> Unit,
    onboardingViewModel: OnboardingViewModel = hiltViewModel()
) {
    val viewState by onboardingViewModel.viewState.collectAsState()

    LaunchedEffect(key1 = onboardingViewModel, block = { onboardingViewModel.fetchGenres() })

    when (val state = viewState) {
        OnboardingViewState.Loading -> LoadingState()
        is OnboardingViewState.LazyGridDisplay -> {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Select Genres",
                    fontSize = 42.sp,
                    lineHeight = 42.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = GameAction,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.size(40.dp))

                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.6f),
                    columns = GridCells.Adaptive(minSize = 100.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(state.genres) { genreItem ->
                        GenreComponent(genreItem) { onboardingViewModel.onSelectGenre(genreItem) }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val scope = rememberCoroutineScope()
                    NextButton(
                        text = "Search"
                    ) {
                        scope.launch {
                            onboardingViewModel.saveOnboardingState(completed = true)
                            onboardingViewModel.saveGenres(state.genres)
                            onFinishClicked()
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(0.5f))
            }
        }
    }
}

@Composable
fun GenreComponent(
    genreItem: Genre,
    onGenreClicked: () -> Unit = {}
) {
    Card(
        modifier = Modifier.wrapContentSize(),
        colors = CardDefaults.cardColors(containerColor = GamePrimaryMonochromatic),
        border = if (genreItem.isSelected) BorderStroke(2.dp, GameAction) else null
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(4.dp)
                .clickable { onGenreClicked() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = genreItem.name,
                color = GameTextSecondary,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun NextButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = GameSurface,
            contentColor = GamePrimary
        ),
        shape = RoundedCornerShape(size = 6.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
        )
    }
}