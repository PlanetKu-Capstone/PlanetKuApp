package com.capstone.planetku.data

data class LoginResponse(
    val message: String,
    val token: String,
    val user: User
)
data class User(
    val id: Int,
    val username: String,
    val name: String,
    val email: String?,
    val role: String
)
