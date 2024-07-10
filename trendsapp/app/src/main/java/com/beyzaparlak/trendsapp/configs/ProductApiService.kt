package com.beyzaparlak.trendsapp.configs


import com.beyzaparlak.trendsapp.models.Cart
import com.beyzaparlak.trendsapp.models.Category
import com.beyzaparlak.trendsapp.models.Product
import com.beyzaparlak.trendsapp.models.Products
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApiService {
    // il 30 product cagrısı
    @GET("products")
    fun getProducts(@Query("limit") limit: Int): Call<Products>

    // category cagrısı
    @GET("products/category/{category}")
    fun getProductsByCategory(@Path("category") category: String): Call<Products>

    // categores cagrısı
    @GET("products/categories")
    fun getCategory(): Call<List<Category>>

    // id ye göre product cagrısı
    @GET("products/{id}")
    fun getProduct(@Path("id") id: Int): Call<Product>

    // search cagrısı
    @GET("products/search")
    fun getSearch(@Query("q") word: String): Call<Products>

    // carts ın userId ile cagrısı
    @POST("carts/user/{userId}")
    fun addToCart(@Path("userId") userId: Int, @Body cart: Cart): Call<CartResponse>
}