package com.example.gameapp.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.gameapp.domain.Genre

@Dao
interface GenreDao {
    @Query("SELECT * FROM genre_table WHERE isSelected")
    suspend fun getSelectedGenres(): List<Genre>

    @Query("SELECT * FROM genre_table")
    suspend fun getAllGenres(): List<Genre>

    @Upsert
    suspend fun addGenres(genres: List<Genre>)
}