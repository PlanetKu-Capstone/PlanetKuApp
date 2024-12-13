package com.capstone.planetku.ui.register

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.planetku.R
import com.capstone.planetku.api.ApiClient
import com.capstone.planetku.data.AuthResponse
import com.capstone.planetku.data.RegisterRequest
import com.capstone.planetku.databinding.ActivityRegisterBinding
import com.capstone.planetku.ui.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        playRegisterAnimations()

        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            when {
                name.isEmpty() -> {
                    binding.etName.error = getString(R.string.name_required)
                }
                username.isEmpty() -> {
                    binding.etUsername.error = getString(R.string.email_required)
                }
                password.isEmpty() -> {
                    binding.etPassword.error = getString(R.string.password_required)
                }
                else -> {
                    registerUser(username, name, password)

                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun registerUser(username: String, name: String, password: String) {
        val registerRequest = RegisterRequest(username, name, password)

        ApiClient.mainService.registerUser(registerRequest).enqueue(object :
            Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    Toast.makeText(this@RegisterActivity, registerResponse?.message, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@RegisterActivity, "Registration failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun playRegisterAnimations() {
        val scaleX = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 1.1f, 1.0f)
        val scaleY = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 1.1f, 1.0f)
        val alpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1f)

        val buttonAnimator = ObjectAnimator.ofPropertyValuesHolder(binding.btnRegister, scaleX, scaleY, alpha)
        buttonAnimator.duration = 1000
        buttonAnimator.start()

        val nameAlphaAnimator = ObjectAnimator.ofFloat(binding.etName, "alpha", 0f, 1f)
        nameAlphaAnimator.duration = 1200
        nameAlphaAnimator.start()

        val usernameAlphaAnimator = ObjectAnimator.ofFloat(binding.etUsername, "alpha", 0f, 1f)
        usernameAlphaAnimator.duration = 1400
        usernameAlphaAnimator.start()

        val passwordAlphaAnimator = ObjectAnimator.ofFloat(binding.etPassword, "alpha", 0f, 1f)
        passwordAlphaAnimator.duration = 1600
        passwordAlphaAnimator.start()
    }
}
