package com.example.gameapp.presentation.common

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.SubcomposeAsyncImage

private val defaultImageModifier = Modifier
    .fillMaxWidth()
    .aspectRatio(1f)

@Composable
fun GameImage(imageUrl: String?, modifier: Modifier = defaultImageModifier) {
    SubcomposeAsyncImage(
        model = imageUrl,
        contentDescription = "Game image",
        modifier = modifier,
        loading = { LoadingState() }
    )
}