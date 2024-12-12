package com.capstone.planetku.data

import android.util.Log
import com.capstone.planetku.api.ApiClient

class ArticleRepository {
    private val articleService = ApiClient.articleService

    suspend fun getArticles(): Result<List<DataItem>> {
        return try {
            val response = articleService.getArticles()
            if (response.isSuccessful) {
                val articles = response.body()?.data ?: emptyList()
                Result.success(articles.filterNotNull())
            } else {
                Result.failure(Exception("Failed to fetch articles"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getArticleBySlugFromAll(slug: String): Result<DataItem> {
        return try {
            // Panggil API getAllArticles
            val articlesResult = getArticles()
            val articles = articlesResult.getOrNull()

            // Lakukan filter pada artikel yang ditemukan
            val article = articles?.find { it.slug == slug }
            if (article != null) {
                Result.success(article)
            } else {
                Result.failure(Exception("Article with slug '$slug' not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getLatestArticles(): Result<List<DataItem>> {
        return try {
            val response = articleService.getLatestArticles()
            if (response.isSuccessful) {
                val articles = response.body()?.data ?: emptyList()
                Result.success(articles.filterNotNull())
            } else {
                Result.failure(Exception("Failed to fetch latest articles"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}