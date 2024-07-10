package com.beyzaparlak.trendsapp.configs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beyzaparlak.trendsapp.models.Product

class ProductSearchViewModel: ViewModel() {

    private val repository = ProductRepository()
    val _products = MutableLiveData<List<Product>>()

    val products: LiveData<List<Product>>
        get() = _products

    // arama islemi
    fun getSearch(word: String) {
        repository.getSearch(word).observeForever { result ->
            _products.value = result
        }
    }
}
