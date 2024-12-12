package com.capstone.planetku.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.planetku.R
import com.capstone.planetku.databinding.ActivityRegisterBinding
import com.capstone.planetku.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            when {
                name.isEmpty() -> {
                    binding.etName.error = getString(R.string.name_required)
                }
                email.isEmpty() -> {
                    binding.etUsername.error = getString(R.string.email_required)
                }
                password.isEmpty() -> {
                    binding.etPassword.error = getString(R.string.password_required)
                }
                else -> {
                    Toast.makeText(this, getString(R.string.register_success), Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}