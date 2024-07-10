package com.beyzaparlak.trendsapp.configs

import com.beyzaparlak.trendsapp.models.Cart

data class CartResponse(
    val carts: List<Cart>,
    val total: Int,
    val skip: Int,
    val limit: Int
)