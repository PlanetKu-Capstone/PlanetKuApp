package com.capstone.planetku.data

import com.google.gson.annotations.SerializedName

data class PredictionRequest(
    @field:SerializedName("item")
    val item: String
)



