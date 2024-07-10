package com.beyzaparlak.trendsapp.models

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val username: String,
    val password: String
)
