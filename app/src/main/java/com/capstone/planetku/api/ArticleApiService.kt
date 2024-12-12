package com.capstone.planetku.api

import com.capstone.planetku.data.ArticleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

// ArticleApiService.kt
interface ArticleApiService {
    @GET("api/getAllArticles")
    suspend fun getArticles(): Response<ArticleResponse>

    @GET("api/showArticle/:slug")
    suspend fun getArticleBySlug(@Path("slug") slug: String): Response<ArticleResponse>

    @GET("api/getLatestArticles")
    suspend fun getLatestArticles(): Response<ArticleResponse>
}