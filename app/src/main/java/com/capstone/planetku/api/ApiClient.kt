package com.capstone.planetku.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    // Endpoint untuk User & Carbon
    private const val MAIN_BASE_URL = "https://main-api-83958552057.asia-southeast2.run.app/"
    // Endpoint untuk Article
    private const val ARTICLE_BASE_URL = "https://article-api-83958552057.asia-southeast2.run.app/"
    // Endpoint untuk ML
    private const val ML_BASE_URL = "https://model-api-373437380047.asia-southeast2.run.app/"

    private val mainRetrofit = Retrofit.Builder()
        .baseUrl(MAIN_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val articleRetrofit = Retrofit.Builder()
        .baseUrl(ARTICLE_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val mlRetrofit = Retrofit.Builder()
        .baseUrl(ML_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val mainService: MainApiService = mainRetrofit.create(MainApiService::class.java)
    val articleService: ArticleApiService = articleRetrofit.create(ArticleApiService::class.java)
    val mlService: MLApiService = mlRetrofit.create(MLApiService::class.java)
}
