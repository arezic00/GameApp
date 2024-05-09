package com.example.gameapp.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre_table")
data class Genre(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val isSelected: Boolean = false
)
