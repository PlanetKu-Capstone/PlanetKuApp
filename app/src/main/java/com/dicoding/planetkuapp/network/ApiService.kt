package com.dicoding.planetkuapp.network
import com.dicoding.planetkuapp.model.RegisterRequest
import com.dicoding.planetkuapp.model.AuthResponse
import com.dicoding.planetkuapp.model.LoginRequest
import com.dicoding.planetkuapp.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
interface ApiService {
    @POST("/register")
    fun registerUser(@Body registerRequest: RegisterRequest): Call<AuthResponse>

    @POST("/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>
}