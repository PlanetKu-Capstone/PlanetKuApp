package com.capstone.planetku.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.capstone.planetku.databinding.ActivityLoginRegisterBinding
import com.capstone.planetku.ui.login.LoginActivity
import com.capstone.planetku.ui.register.RegisterActivity

class LoginRegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityLoginRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupInitialAnimations()
        setupClickListeners()
    }

    private fun setupInitialAnimations() {
        binding.btnLogin.alpha = 0f
        binding.btnRegister.alpha = 0f
        binding.btnLogin.translationY = 50f
        binding.btnRegister.translationY = 50f

        ObjectAnimator.ofFloat(binding.btnLogin, "alpha", 0f, 1f).apply {
            duration = 500
            start()
        }
        ObjectAnimator.ofFloat(binding.btnLogin, "translationY", 50f, 0f).apply {
            duration = 500
            start()
        }

        ObjectAnimator.ofFloat(binding.btnRegister, "alpha", 0f, 1f).apply {
            startDelay = 100
            duration = 500
            start()
        }
        ObjectAnimator.ofFloat(binding.btnRegister, "translationY", 50f, 0f).apply {
            startDelay = 100
            duration = 500
            start()
        }
    }

    private fun setupClickListeners() {
        binding.btnLogin.setOnClickListener { view ->
            playClickAnimation(view) {
                getSharedPreferences("AppPreferences", MODE_PRIVATE)
                    .edit()
                    .putBoolean("IS_FIRST_LAUNCH", false)
                    .apply()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        binding.btnRegister.setOnClickListener { view ->
            playClickAnimation(view) {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun playClickAnimation(view: View, onAnimationEnd: () -> Unit) {
        val scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.95f, 1.05f, 1f)
        val scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.95f, 1.05f, 1f)

        scaleX.duration = 300
        scaleY.duration = 300

        scaleX.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                onAnimationEnd.invoke()
            }
        })

        scaleX.start()
        scaleY.start()
    }
}