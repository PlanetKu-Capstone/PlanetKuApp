package com.capstone.planetku.ui.login

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.planetku.MainActivity
import com.capstone.planetku.R
import com.capstone.planetku.api.ApiClient
import com.capstone.planetku.data.LoginRequest
import com.capstone.planetku.data.LoginResponse
import com.capstone.planetku.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressBar = binding.progressBar

        playLoginAnimations()

        binding.btnLogin.setOnClickListener {
            val email = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (validateInput(email, password)) {
                performLogin(email, password)
            }
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            binding.usernameInputLayout.error = getString(R.string.error_username_required)
            return false
        } else {
            binding.usernameInputLayout.error = null
        }

        if (password.isEmpty()) {
            binding.passwordInputLayout.error = getString(R.string.error_password_required)
            return false
        } else {
            binding.passwordInputLayout.error = null
        }

        return true
    }

    private fun performLogin(username: String, password: String) {
        val loginRequest = LoginRequest(username, password)

        progressBar.visibility = View.VISIBLE

        ApiClient.mainService.loginUser(loginRequest).enqueue(object :
            Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                progressBar.visibility = View.GONE

                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    loginResponse?.let {
                        saveUserDataToLocalStorage(it)

                        Toast.makeText(this@LoginActivity, it.message, Toast.LENGTH_LONG).show()

                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                Toast.makeText(this@LoginActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun saveUserDataToLocalStorage(loginResponse: LoginResponse) {
        val sharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("token", loginResponse.token)
        editor.putInt("user_id", loginResponse.user.id)
        editor.putString("username", loginResponse.user.username)
        editor.putString("name", loginResponse.user.name)
        editor.putString("role", loginResponse.user.role)

        editor.putBoolean("IS_FIRST_LAUNCH", false)
        editor.apply()
    }

    private fun playLoginAnimations() {
        val scaleX = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 1.1f, 1.0f)
        val scaleY = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 1.1f, 1.0f)
        val alpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1f)

        val buttonAnimator = ObjectAnimator.ofPropertyValuesHolder(binding.btnLogin, scaleX, scaleY, alpha)
        buttonAnimator.duration = 1000
        buttonAnimator.start()

        val usernameAlphaAnimator = ObjectAnimator.ofFloat(binding.usernameInputLayout, "alpha", 0f, 1f)
        usernameAlphaAnimator.duration = 1200
        usernameAlphaAnimator.start()

        val passwordAlphaAnimator = ObjectAnimator.ofFloat(binding.passwordInputLayout, "alpha", 0f, 1f)
        passwordAlphaAnimator.duration = 1400
        passwordAlphaAnimator.start()
    }
}
