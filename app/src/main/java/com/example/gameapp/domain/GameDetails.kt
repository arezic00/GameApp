package com.example.gameapp.domain

data class GameDetails(
    val name: String,
    val released: String,
    val rating: Float,
    val imageUrl: String,
    val genres: List<String>,
    val platforms: List<String>,
    val developerTeams: List<String>,
    val publishers: List<String>,
    val description: String,
    val updated: String
)
