package com.capstone.planetku.api

import com.capstone.planetku.data.ArticleResponse
import retrofit2.Response
import retrofit2.http.GET

interface ArticleApiService {
    @GET("api/getAllArticles")
    suspend fun getArticles(): Response<ArticleResponse>

    @GET("api/getLatestArticles")
    suspend fun getLatestArticles(): Response<ArticleResponse>
}