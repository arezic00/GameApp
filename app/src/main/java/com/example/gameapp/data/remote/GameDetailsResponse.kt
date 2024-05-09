package com.example.gameapp.data.remote

import com.example.gameapp.domain.GameDetails

data class GameDetailsResponse(
    val name: String,
    val description_raw: String,
    val released: String,
    val updated: String,
    val background_image: String,
    val rating: Float,
    val parent_platforms: List<ParentPlatform>,
    val developers: List<GameDevelopers>,
    val genres: List<RemoteGenre>,
    val publishers: List<GamePublisher>,
)

fun GameDetailsResponse.toGameDetails(): GameDetails {
    return GameDetails(
        name = name,
        description = description_raw,
        released = released,
        updated = updated,
        imageUrl = background_image,
        rating = rating,
        platforms = parent_platforms.map { it.platform.name },
        developerTeams = developers.map { it.name },
        genres = genres.map { it.name },
        publishers = publishers.map { it.name }
    )
}
