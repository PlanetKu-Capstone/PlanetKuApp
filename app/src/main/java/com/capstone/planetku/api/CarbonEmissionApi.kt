package com.capstone.planetku.api

data class CarbonEmissionRequest(
    val electricity: Double,
    val gas: Double,
    val transportation: Double,
    val food: Double,
    val organic_waste: Double,
    val inorganic_waste: Double
)

data class CarbonEmissionResponse(
    val predicted_carbon_footprint: Double
)

data class CarbonData(
    val category: String,
    val amount: Float,
    val date: String
)