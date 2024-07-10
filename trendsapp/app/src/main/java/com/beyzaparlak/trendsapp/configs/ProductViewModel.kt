package com.beyzaparlak.trendsapp.configs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beyzaparlak.trendsapp.models.Product

class ProductViewModel : ViewModel() {
    private val repository = ProductRepository()
    private val _products = MutableLiveData<List<Product>>()

    val products: LiveData<List<Product>>
        get() = _products

    // ilk 30 product
    fun getProducts(limit: Int = 30): LiveData<List<Product>> {
        return repository.getProducts(limit)
    }

    fun loadProductsByCategory(category: String) {
        repository.getProductsByCategory(category).observeForever { products ->
            _products.value = products
        }
    }

}