package com.capstone.planetku.ui.more

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.fragment.app.Fragment
import com.capstone.planetku.R
import com.capstone.planetku.databinding.FragmentMoreBinding
import com.capstone.planetku.ui.about.AboutActivity
import com.capstone.planetku.ui.login.LoginActivity
import com.capstone.planetku.ui.profile.ProfileActivity
import com.capstone.planetku.ui.welcome.WelcomeActivity
import com.google.android.material.animation.AnimatorSetCompat.playTogether

class MoreFragment : Fragment(R.layout.fragment_more) {

    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding!!
    private val animatorSet = AnimatorSet()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMoreBinding.bind(view)

        setupClickAnimations()
    }

    private fun setupClickAnimations() {
        val views = listOf(binding.tabProfile, binding.tabAboutApp, binding.tabLogout)

        views.forEach { view ->
            view.setOnClickListener { clickedView ->
                playClickAnimation(clickedView) {
                    when (clickedView) {
                        binding.tabProfile -> navigateToProfile()
                        binding.tabAboutApp -> navigateToAbout()
                        binding.tabLogout -> handleLogout()
                    }
                }
            }
        }
    }

    private fun playClickAnimation(view: View, onAnimationEnd: () -> Unit) {
        animatorSet.cancel()

        val scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.95f, 1.05f, 1f)
        val scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.95f, 1.05f, 1f)

        val alpha = ObjectAnimator.ofFloat(view, "alpha", 1f, 0.8f, 1f)

        animatorSet.apply {
            playTogether(scaleX, scaleY, alpha)
            duration = 300
            interpolator = OvershootInterpolator(1.5f)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    onAnimationEnd.invoke()
                }
            })
            start()
        }
    }

    private fun navigateToProfile() {
        startActivity(Intent(requireContext(), ProfileActivity::class.java))
    }

    private fun navigateToAbout() {
        startActivity(Intent(requireContext(), AboutActivity::class.java))
    }

    private fun handleLogout() {
        requireContext().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
            .edit()
            .putBoolean("IS_FIRST_LAUNCH", true)
            .putBoolean("IS_LOGGED_OUT", true)
            .apply()

        startActivity(Intent(requireContext(), WelcomeActivity::class.java))
        activity?.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        animatorSet.cancel()
        _binding = null
    }
}


