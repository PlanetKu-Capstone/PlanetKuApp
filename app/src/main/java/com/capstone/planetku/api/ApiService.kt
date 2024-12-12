package com.capstone.planetku.api

import com.capstone.planetku.data.PredictionRequest
import com.capstone.planetku.data.PredictionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("predict")
    suspend fun getPrediction(@Body request: PredictionRequest): Response<PredictionResponse>
}