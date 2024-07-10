package com.beyzaparlak.trendsapp.models

data class Cart(
    val userId: Int,
    val products: List<CartItem>
)