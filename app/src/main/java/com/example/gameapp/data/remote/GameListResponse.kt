package com.example.gameapp.data.remote

import com.example.gameapp.domain.GamePage

class GameListResponse(
    val results: List<GameListItemResponse>
)

fun GameListResponse.toGamePage(): GamePage {
    return GamePage(
        games = results.map { it.toGame() }
    )
}