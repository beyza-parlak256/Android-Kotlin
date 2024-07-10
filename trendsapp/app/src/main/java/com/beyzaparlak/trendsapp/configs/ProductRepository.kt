package com.beyzaparlak.trendsapp.configs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beyzaparlak.trendsapp.models.Product
import com.beyzaparlak.trendsapp.models.Products
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository {
    private val apiService: ProductApiService = RetrofitClient.apiService
    // product erisim
    fun getProducts(limit: Int = 30): LiveData<List<Product>> {
        val data = MutableLiveData<List<Product>>()
        apiService.getProducts(limit).enqueue(object : Callback<Products> {
            override fun onResponse(call: Call<Products>, response: Response<Products>) {
                if (response.isSuccessful) {
                    data.value = response.body()?.products ?: emptyList()
                } else {
                    // Hata durumları
                    data.value = emptyList()
                }
            }
            override fun onFailure(call: Call<Products>, t: Throwable) {
                // Ağ hatası durumları
                data.value = emptyList()
            }
        })
        return data
    }

    // kategoriye göre
    fun getProductsByCategory(category: String): LiveData<List<Product>> {
        val data = MutableLiveData<List<Product>>()
        apiService.getProductsByCategory(category).enqueue(object : Callback<Products> {
            override fun onResponse(call: Call<Products>, response: Response<Products>) {
                if (response.isSuccessful) {
                    data.value = response.body()?.products ?: emptyList()
                } else {
                    // Hata durumları
                    data.value = emptyList()
                }
            }
            override fun onFailure(call: Call<Products>, t: Throwable) {
                // Ağ hatası durumları
                data.value = emptyList()
            }
        })
        return data
    }

    // ürün arama
    fun getSearch(word: String): LiveData<List<Product>> {
        val data = MutableLiveData<List<Product>>()
        apiService.getSearch(word).enqueue(object : Callback<Products> {
            override fun onResponse(call: Call<Products>, response: Response<Products>) {
                if (response.isSuccessful) {
                    data.value = response.body()?.products ?: emptyList()
                } else {
                    data.value = emptyList() // Hata durumları
                }
            }
            override fun onFailure(call: Call<Products>, t: Throwable) {
                data.value = emptyList() // Ağ hatası durumları
            }
        })
        return data
    }
}