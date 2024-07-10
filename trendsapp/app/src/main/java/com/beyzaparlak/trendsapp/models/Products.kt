package com.beyzaparlak.trendsapp.models

data class Products(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)