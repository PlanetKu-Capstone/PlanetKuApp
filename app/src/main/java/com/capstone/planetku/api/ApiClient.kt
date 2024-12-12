package com.capstone.planetku.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://main-api-83958552057.asia-southeast2.run.app/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val predictionService: ApiService = retrofit.create(ApiService::class.java)
}