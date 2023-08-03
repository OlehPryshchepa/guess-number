package com.example.random_number_game.controller

import retrofit2.http.GET

/**
 * Retrofit API interface for making API calls to the random.org API.
 */
interface RandomOrgApi {
    /**
     * Fetches a random number from the random.org API.
     *
     * @return A random number between 1 and 100 (inclusive).
     */
    @GET("integers/?num=1&min=1&max=100&col=1&base=10&format=plain&rnd=new")
    suspend fun getRandomNumber(): Int
}