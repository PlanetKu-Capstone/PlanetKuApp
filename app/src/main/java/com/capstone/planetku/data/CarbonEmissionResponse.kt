package com.capstone.planetku.data

import com.google.gson.annotations.SerializedName

data class CarbonEmissionResponse(

	@field:SerializedName("predicted_carbon_footprint")
	val predictedCarbonFootprint: Any? = null
)
