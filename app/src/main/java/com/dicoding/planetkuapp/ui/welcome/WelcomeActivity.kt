package com.dicoding.planetkuapp.ui.welcome

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.planetkuapp.databinding.ActivityWelcomeBinding
import com.dicoding.planetkuapp.ui.LoginRegisterActivity
import com.dicoding.planetkuapp.ui.login.LoginActivity
import com.dicoding.planetkuapp.ui.register.RegisterActivity

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStarted.setOnClickListener {
            val intent = Intent(this, LoginRegisterActivity::class.java)
            startActivity(intent)
        }
    }
}