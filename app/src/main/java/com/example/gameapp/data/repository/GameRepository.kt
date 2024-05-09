package com.example.gameapp.data.repository

import com.example.gameapp.data.remote.GameApi
import com.example.gameapp.data.remote.toGameDetails
import com.example.gameapp.data.remote.toGamePage
import com.example.gameapp.domain.GameDetails
import com.example.gameapp.domain.GamePage
import com.example.gameapp.domain.Genre
import javax.inject.Inject

class GameRepository @Inject constructor(private val gameApi: GameApi) {
    suspend fun fetchGamePage(page: Int, selectedGenresString: String?): ApiOperation<GamePage> {
        return if (selectedGenresString.isNullOrBlank())
            safeApiCall { gameApi.getGames(page).toGamePage() }
        else
            safeApiCall { gameApi.getGamesByGenres(page, selectedGenresString).toGamePage() }
    }

    suspend fun fetchGameDetails(gameId: Int): ApiOperation<GameDetails> {
        return safeApiCall {
            gameApi.getGameDetails(gameId).toGameDetails()
        }
    }

    suspend fun fetchGenres(): ApiOperation<List<Genre>> {
        return safeApiCall {
            gameApi.getGenres().results
        }
    }

    private inline fun <T> safeApiCall(apiCall: () -> T): ApiOperation<T> {
        return try {
            ApiOperation.Success(data = apiCall())
        } catch (e: Exception) {
            ApiOperation.Failure(exception = e)
        }
    }
}

sealed interface ApiOperation<T> {
    data class Success<T>(val data: T) : ApiOperation<T>
    data class Failure<T>(val exception: Exception) : ApiOperation<T>

    fun onSuccess(block: (T) -> Unit): ApiOperation<T> {
        if (this is Success) block(data)
        return this
    }

    fun onFailure(block: (Exception) -> Unit): ApiOperation<T> {
        if (this is Failure) block(exception)
        return this
    }
}