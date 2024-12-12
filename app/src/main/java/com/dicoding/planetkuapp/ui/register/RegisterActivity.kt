package com.dicoding.planetkuapp.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.planetkuapp.R
import com.dicoding.planetkuapp.RetrofitInstance
import com.dicoding.planetkuapp.databinding.ActivityRegisterBinding
import com.dicoding.planetkuapp.model.RegisterRequest
import com.dicoding.planetkuapp.model.AuthResponse
import com.dicoding.planetkuapp.ui.login.LoginActivity
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

        RetrofitInstance.retrofitInstance.registerUser(registerRequest).enqueue(object : Callback<AuthResponse> {
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
}