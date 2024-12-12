package com.capstone.planetku.api

import com.capstone.planetku.data.PredictionRequest
import com.capstone.planetku.data.PredictionResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MLApiService {
    @POST("predict")
    suspend fun getPrediction(@Body request: PredictionRequest): Response<PredictionResponse>

    // Endpoint untuk prediksi emisi karbon
    @POST("/predict")
    fun predictCarbonEmission(
        @Body request: CarbonEmissionRequest
    ): Call<CarbonEmissionResponse>
    // Endpoint untuk mendapatkan data karbon umum
    @GET("/api/carbon")
    fun getCarbonData(): Call<List<CarbonData>>
    // Endpoint untuk menambahkan data karbon umum
    @POST("/api/carbon")
    fun addCarbonData(@Body data: CarbonData): Call<Void>


}