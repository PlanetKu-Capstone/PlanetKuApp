package com.capstone.planetku.ui.priceprediction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.planetku.api.ApiClient
import com.capstone.planetku.data.PredictionRequest
import com.capstone.planetku.data.PredictionResponse
import kotlinx.coroutines.launch

class PricePredictionViewModel : ViewModel() {
    private val _predictionResult = MutableLiveData<Result<PredictionResponse>>()
    val predictionResult: LiveData<Result<PredictionResponse>> = _predictionResult

    fun predictPrice(wasteType: String) {
        viewModelScope.launch {
            try {
                val response = ApiClient.mlService.getPrediction(PredictionRequest(wasteType))
                if (response.isSuccessful) {
                    response.body()?.let {
                        _predictionResult.value = Result.success(it)
                    }
                } else {
                    _predictionResult.value = Result.failure(Exception("Gagal mendapatkan prediksi harga"))
                }
            } catch (e: Exception) {
                _predictionResult.value = Result.failure(e)
            }
        }
    }
}