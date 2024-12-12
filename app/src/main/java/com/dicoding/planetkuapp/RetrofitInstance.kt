package com.dicoding.planetkuapp

import com.dicoding.planetkuapp.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://main-api-83958552057.asia-southeast2.run.app"  // Ganti dengan URL API kamu

    val retrofitInstance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}