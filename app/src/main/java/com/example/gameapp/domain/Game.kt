package com.example.gameapp.domain

data class Game(
    val id: Int,
    val name: String,
    val released: String,
    val rating: Float,
    val backgroundImageUrl: String?
)
