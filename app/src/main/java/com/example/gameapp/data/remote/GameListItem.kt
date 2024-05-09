package com.example.gameapp.data.remote

import com.example.gameapp.domain.Game

data class GameListItemResponse(
    val id: Int,
    val name: String,
    val released: String,
    val rating: Float,
    val background_image: String
)

fun GameListItemResponse.toGame(): Game {
    return Game(
        id = id,
        name = name,
        released = released,
        rating = rating,
        backgroundImageUrl = background_image
    )
}
