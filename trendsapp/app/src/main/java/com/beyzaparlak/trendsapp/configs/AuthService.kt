package com.beyzaparlak.trendsapp.configs

import com.beyzaparlak.trendsapp.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthService {
    // login
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    // user id
    @GET("users/{id}")
    suspend fun getUser(@Path("id") userId: Int): Response<User>
}