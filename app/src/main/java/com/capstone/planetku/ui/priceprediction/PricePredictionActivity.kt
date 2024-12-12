package com.capstone.planetku.ui.priceprediction

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.planetku.databinding.ActivityPricePredictionBinding

class PricePredictionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPricePredictionBinding
    private val wasteTypes = listOf("plastic", "paper", "metal", "glass", "organic")
    private val viewModel: PricePredictionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPricePredictionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val classifiedWaste = intent.getStringExtra("CLASSIFIED_WASTE")
        setupSpinner(classifiedWaste)
        setupObservers()

        binding.btnPredictPrice.setOnClickListener {
            val selectedWasteType = binding.spWasteType.selectedItem.toString()
            // Tampilkan loading
            binding.progressBar.visibility = View.VISIBLE
            binding.tvPredictionResult.visibility = View.GONE

            viewModel.predictPrice(selectedWasteType)
        }
    }

    private fun setupSpinner(defaultWasteType: String?) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, wasteTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spWasteType.adapter = adapter

        defaultWasteType?.let {
            val position = wasteTypes.indexOf(it)
            if (position >= 0) {
                binding.spWasteType.setSelection(position)
            }
        }
    }

    private fun setupObservers() {
        viewModel.predictionResult.observe(this) { result ->
            binding.progressBar.visibility = View.GONE

            result.onSuccess { prediction ->
                binding.tvPredictionResult.apply {
                    text = "Estimasi harga untuk ${prediction.item} adalah Rp${prediction.predicted_price}/kg"
                    visibility = View.VISIBLE
                }
            }.onFailure { exception ->
                binding.tvPredictionResult.apply {
                    text = "Gagal mendapatkan prediksi: ${exception.message}"
                    visibility = View.VISIBLE
                }
            }
        }
    }
}

