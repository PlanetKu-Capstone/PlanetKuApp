package com.dicoding.planetkuapp.ui.welcome

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.planetkuapp.MainActivity
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



        val sharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val isFirstLaunch = sharedPreferences.getBoolean("IS_FIRST_LAUNCH", true)

        if (!isFirstLaunch) {
            // Jika sudah pernah login, arahkan langsung ke MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            // Jika pertama kali dijalankan, ubah status IS_FIRST_LAUNCH menjadi false
            val editor = sharedPreferences.edit()
            editor.putBoolean("IS_FIRST_LAUNCH", false)
            editor.apply()

            // Arahkan ke LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

//        binding.btnLogin.setOnClickListener {
//            val sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
//            sharedPreferences.edit().putBoolean("IS_FIRST_LAUNCH", false).apply()
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//        }
//
//        binding.btnRegister.setOnClickListener {
//            val intent = Intent(this, RegisterActivity::class.java)
//            startActivity(intent)
//        }
//        val btnGoToSecondActivity = findViewById<Button>(R.id.btnGoToSecondActivity)
        binding.btnStarted.setOnClickListener {
            Log.d("WelcomeActivity", "Button clicked!")
            val intent = Intent(this, LoginRegisterActivity::class.java)
            startActivity(intent)
        }
    }
}