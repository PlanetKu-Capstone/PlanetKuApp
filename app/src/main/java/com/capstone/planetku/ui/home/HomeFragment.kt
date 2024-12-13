package com.capstone.planetku.ui.home

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
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

        val sharedPreferences = requireContext().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("username", "User")

        binding.tvHiUser.text = "Hi, $userName!"

        playHomeAnimations()

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

    private fun playHomeAnimations() {
        val textAlpha = ObjectAnimator.ofFloat(binding.tvHiUser, "alpha", 0f, 1f)
        textAlpha.duration = 1000
        textAlpha.start()

        val klasifikasiScaleX = PropertyValuesHolder.ofFloat("scaleX", 0.8f, 1.0f)
        val klasifikasiScaleY = PropertyValuesHolder.ofFloat("scaleY", 0.8f, 1.0f)
        val klasifikasiAnimator = ObjectAnimator.ofPropertyValuesHolder(
            binding.btnKlasifikasi,
            klasifikasiScaleX,
            klasifikasiScaleY
        )
        klasifikasiAnimator.duration = 800
        klasifikasiAnimator.start()

        val emisiScaleX = PropertyValuesHolder.ofFloat("scaleX", 0.8f, 1.0f)
        val emisiScaleY = PropertyValuesHolder.ofFloat("scaleY", 0.8f, 1.0f)
        val emisiAnimator = ObjectAnimator.ofPropertyValuesHolder(
            binding.btnEmisi,
            emisiScaleX,
            emisiScaleY
        )
        emisiAnimator.duration = 800
        emisiAnimator.startDelay = 200
        emisiAnimator.start()

        val prediksiScaleX = PropertyValuesHolder.ofFloat("scaleX", 0.8f, 1.0f)
        val prediksiScaleY = PropertyValuesHolder.ofFloat("scaleY", 0.8f, 1.0f)
        val prediksiAnimator = ObjectAnimator.ofPropertyValuesHolder(
            binding.btnPrediksi,
            prediksiScaleX,
            prediksiScaleY
        )
        prediksiAnimator.duration = 800
        prediksiAnimator.startDelay = 400
        prediksiAnimator.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
