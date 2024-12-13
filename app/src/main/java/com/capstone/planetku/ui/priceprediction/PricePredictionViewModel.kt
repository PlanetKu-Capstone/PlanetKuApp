package com.capstone.planetku.ui.priceprediction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.planetku.api.ApiClient
import com.capstone.planetku.data.PredictionRequest
import com.capstone.planetku.data.PredictionResponse
import kotlinx.coroutines.launch
import retrofit2.HttpException

class PricePredictionViewModel : ViewModel() {

    private val _predictionResult = MutableLiveData<Result<PredictionResponse>>()
    val predictionResult: LiveData<Result<PredictionResponse>> = _predictionResult

    fun predictPrice(wasteType: String) {
        viewModelScope.launch {
            try {
                val request = PredictionRequest(item = wasteType)

                val response = ApiClient.mlService.getPrediction(request)

                if (response.isSuccessful && response.body() != null) {
                    _predictionResult.value = Result.success(response.body()!!)
                } else {
                    _predictionResult.value = Result.failure(
                        Exception("Gagal mendapatkan prediksi: ${response.errorBody()?.string()}")
                    )
                }
            } catch (e: HttpException) {
                _predictionResult.value = Result.failure(Exception("HTTP Error: ${e.message}"))
            } catch (e: Exception) {
                _predictionResult.value = Result.failure(Exception("Error: ${e.message}"))
            }
        }
    }
}