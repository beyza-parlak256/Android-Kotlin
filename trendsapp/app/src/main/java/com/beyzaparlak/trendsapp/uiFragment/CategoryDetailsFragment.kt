package com.beyzaparlak.trendsapp.uiFragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beyzaparlak.trendsapp.ProductActivty
import com.beyzaparlak.trendsapp.R
import com.beyzaparlak.trendsapp.configs.ProductApiService
import com.beyzaparlak.trendsapp.configs.RetrofitClient
import com.beyzaparlak.trendsapp.dao.ProductAdapter
import com.beyzaparlak.trendsapp.models.Product
import com.beyzaparlak.trendsapp.models.Products
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryDetailsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private lateinit var apiService: ProductApiService
    private lateinit var categoryName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewCategoryProducts)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        apiService = RetrofitClient.apiService

        // Bundle'dan kategori adını alır
        categoryName = arguments?.getString("categoryName") ?: ""

        // Kategoriye göre ürünleri alır ve RecyclerView'e yükler
        apiService.getProductsByCategory(categoryName).enqueue(object : Callback<Products> {
            override fun onResponse(call: Call<Products>, response: Response<Products>) {
                if (response.isSuccessful) {
                    val products = response.body()?.products ?: emptyList()
                    adapter = ProductAdapter(products, object : ProductAdapter.OnItemClickListener {
                        override fun onItemClick(product: Product) {
                            val intent = Intent(requireContext(), ProductActivty::class.java)
                            intent.putExtra("productId", product.id)
                            startActivity(intent)
                        }
                    })
                    recyclerView.adapter = adapter
                } else {
                    Log.e("getProductsByCategory", "Response not successful")
                }
            }

            override fun onFailure(call: Call<Products>, t: Throwable) {
                Log.e("getProductsByCategory", t.message ?: "Unknown error")
            }
        })
    }
}