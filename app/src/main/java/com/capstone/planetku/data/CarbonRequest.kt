package com.capstone.planetku.data

import com.google.gson.annotations.SerializedName

data class CarbonRequest(

	@field:SerializedName("organic_waste")
	val organicWaste: Int? = null,

	@field:SerializedName("inorganic_waste")
	val inorganicWaste: Int? = null,

	@field:SerializedName("gas")
	val gas: Int? = null,

	@field:SerializedName("electricity")
	val electricity: Int? = null,

	@field:SerializedName("food")
	val food: Int? = null,

	@field:SerializedName("transportation")
	val transportation: Int? = null
)
