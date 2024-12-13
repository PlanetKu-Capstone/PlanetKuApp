package com.capstone.planetku.api

import com.capstone.planetku.data.CarbonEmissionResponse
import com.capstone.planetku.data.PredictionRequest
import com.capstone.planetku.data.PredictionResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MLApiService {
    @POST("predict-price") // Ganti dengan endpoint POST API Anda
    suspend fun getPrediction(@Body request: PredictionRequest): Response<PredictionResponse>

    @GET("predict-carbons/{id}") // Endpoint untuk get hasil
    suspend fun getCarbonEmission(@Path("id") id: String): Response<CarbonEmissionResponse>
}