package com.example.gameapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gameapp.domain.Genre

@Database(entities = [Genre::class], version = 1)
abstract class GenreDatabase : RoomDatabase() {
    abstract fun genreDao(): GenreDao
}