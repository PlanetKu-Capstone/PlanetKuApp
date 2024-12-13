package com.capstone.planetku.data

import com.google.gson.annotations.SerializedName

data class PredictionResponse(
    @field:SerializedName("item")
    val item: String,

    @field:SerializedName("predicted_price")
    val predictedPrice: Double
)
