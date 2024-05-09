package com.example.gameapp.di

import android.content.Context
import androidx.room.Room
import com.example.gameapp.common.Constants
import com.example.gameapp.data.local.GenreDatabase
import com.example.gameapp.data.remote.GameApi
import com.example.gameapp.data.repository.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGameApi(): GameApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideDataStoreRepository(
        @ApplicationContext context: Context
    ) = DataStoreRepository(context = context)

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): GenreDatabase {
        return Room.databaseBuilder(
            context,
            GenreDatabase::class.java,
            "genre_database"
        ).build()
    }
}