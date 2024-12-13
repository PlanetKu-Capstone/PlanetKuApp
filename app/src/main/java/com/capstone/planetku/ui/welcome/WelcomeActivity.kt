package com.capstone.planetku.ui.welcome

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.capstone.planetku.MainActivity
import com.capstone.planetku.databinding.ActivityWelcomeBinding
import com.capstone.planetku.ui.LoginRegisterActivity
import com.capstone.planetku.ui.login.LoginActivity

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val isFirstLaunch = sharedPreferences.getBoolean("IS_FIRST_LAUNCH", true)

        if (isFirstLaunch) {
            val editor = sharedPreferences.edit()
            editor.putBoolean("IS_FIRST_LAUNCH", false)
            editor.apply()
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        playWelcomeAnimations()

        binding.btnStarted.setOnClickListener {
            val intent = Intent(this, LoginRegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun playWelcomeAnimations() {
        val scaleX = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 1.2f, 1.0f)
        val scaleY = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 1.2f, 1.0f)
        val alpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1f)

        val buttonAnimator = ObjectAnimator.ofPropertyValuesHolder(binding.btnStarted, scaleX, scaleY, alpha)
        buttonAnimator.duration = 1000
        buttonAnimator.start()

        val textAlphaAnimator = ObjectAnimator.ofFloat(binding.tvWelcomeSelamat, "alpha", 0f, 1f)
        textAlphaAnimator.duration = 1500
        textAlphaAnimator.start()
    }
}