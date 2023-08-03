package com.example.random_number_game.util

import com.example.random_number_game.controller.RandomOrgApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton object to manage the Retrofit client for communication with the random.org API.
 */
object RetrofitClient {
    private const val BASE_URL = "https://www.random.org/"

    /**
     * Lazy-initialized Retrofit instance.
     */
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Lazy-initialized Retrofit API interface for random.org API calls.
     */
    val randomOrgApi: RandomOrgApi by lazy {
        retrofit.create(RandomOrgApi::class.java)
    }
}
