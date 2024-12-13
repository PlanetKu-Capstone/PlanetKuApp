package com.capstone.planetku.api

import com.capstone.planetku.data.AuthResponse
import com.capstone.planetku.data.LoginRequest
import com.capstone.planetku.data.LoginResponse
import com.capstone.planetku.data.RegisterRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MainApiService {
    @POST("/register")
    fun registerUser(@Body registerRequest: RegisterRequest): Call<AuthResponse>
    @POST("/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>
}