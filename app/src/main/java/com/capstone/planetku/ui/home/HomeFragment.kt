package com.capstone.planetku.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.planetku.databinding.FragmentHomeBinding
import com.capstone.planetku.ui.carbonemission.CarbonEmissionActivity
import com.capstone.planetku.ui.classification.WasteClassificationActivity
import com.capstone.planetku.ui.priceprediction.PricePredictionActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnKlasifikasi.setOnClickListener {
            startActivity(Intent(requireContext(), WasteClassificationActivity::class.java))
        }

        binding.btnEmisi.setOnClickListener {
            startActivity(Intent(requireContext(), CarbonEmissionActivity::class.java))
        }

        binding.btnPrediksi.setOnClickListener {
            startActivity(Intent(requireContext(), PricePredictionActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}