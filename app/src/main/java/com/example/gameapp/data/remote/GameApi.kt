package com.example.gameapp.data.remote

import com.example.gameapp.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GameApi {

    @GET("games")
    suspend fun getGamesByGenres(
        @Query("page") page: Int,
        @Query("genres") genres: String,
        @Query("key") key: String = BuildConfig.API_KEY
    ): GameListResponse

    @GET("games")
    suspend fun getGames(
        @Query("page") page: Int,
        @Query("key") key: String = BuildConfig.API_KEY
    ): GameListResponse

    @GET("games/{id}")
    suspend fun getGameDetails(
        @Path("id") id: Int,
        @Query("key") key: String = BuildConfig.API_KEY
    ): GameDetailsResponse

    @GET("genres")
    suspend fun getGenres(
        @Query("key") key: String = BuildConfig.API_KEY
    ): GenresResponse
}