package com.example.random_number_game.service.impl

import com.example.random_number_game.util.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Service class for fetching a random number from the random.org API.
 */
class RandomOrgServiceImpl {
    /**
     * Fetches a random number from the random.org API using Retrofit.
     *
     * This function is a suspend function, allowing it to be safely called from a coroutine.
     *
     * @return A random number between 1 and 100 (inclusive).
     */
    suspend fun fetchRandomNumber(): Int {
        return withContext(Dispatchers.IO) {
            RetrofitClient.randomOrgApi.getRandomNumber()
        }
    }
}