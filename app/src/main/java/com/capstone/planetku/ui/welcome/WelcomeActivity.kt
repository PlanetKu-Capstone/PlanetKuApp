package com.capstone.planetku.ui.welcome

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
        val isLoggedOut = sharedPreferences.getBoolean("IS_LOGGED_OUT", false)

        when {
            isLoggedOut -> {
                val editor = sharedPreferences.edit()
                editor.putBoolean("IS_LOGGED_OUT", false)
                editor.apply()
            }
            !isFirstLaunch -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else -> {
                val editor = sharedPreferences.edit()
                editor.putBoolean("IS_FIRST_LAUNCH", false)
                editor.apply()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.btnStarted.setOnClickListener {
            val intent = Intent(this, LoginRegisterActivity::class.java)
            startActivity(intent)
        }
    }

}