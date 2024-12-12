package com.dicoding.planetkuapp.model

data class LoginResponse(
    val message: String,       // Pesan login (contoh: "Login successful")
    val token: String,         // Token autentikasi
    val user: User             // Data pengguna (id, username, nama, dll)
)

data class User(
    val id: Int,               // ID pengguna
    val username: String,      // Nama pengguna
    val name: String,          // Nama lengkap pengguna
    val email: String?,        // Email pengguna (bisa null)
    val role: String           // Peran pengguna (contoh: "user", "admin")
)
