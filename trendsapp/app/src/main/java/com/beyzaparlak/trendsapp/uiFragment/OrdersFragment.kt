package com.beyzaparlak.trendsapp.uiFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.beyzaparlak.trendsapp.R
import com.beyzaparlak.trendsapp.configs.RetrofitClient
import com.beyzaparlak.trendsapp.models.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrdersFragment : Fragment() {

    private lateinit var listViewProducts: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var textViewTitle: TextView
    private lateinit var imageViewIcon: ImageView

    companion object {
        private const val ARG_PRODUCT_ID = "productId"

        fun newInstance(productId: Int): OrdersFragment {
            val fragment = OrdersFragment()
            val args = Bundle()
            args.putInt(ARG_PRODUCT_ID, productId)
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_orders, container, false)

        listViewProducts = view.findViewById(R.id.listViewProducts)
        adapter = ArrayAdapter(requireContext(), R.layout.orders_row)
        listViewProducts.adapter = adapter

        textViewTitle = view.findViewById(R.id.textView3)
        imageViewIcon = view.findViewById(R.id.imageView10)

        val productId = arguments?.getInt(ARG_PRODUCT_ID, -1)
        if (productId != null && productId != -1) {
            // API'den ürün detaylarını alır ve adapter'a ekler
            RetrofitClient.apiService.getProduct(productId).enqueue(object : Callback<Product> {
                override fun onResponse(call: Call<Product>, response: Response<Product>) {
                    if (response.isSuccessful) {
                        val product = response.body()
                        if (product != null) {
                            val productDetails = "${product.title} - ${product.price}$"
                            adapter.add(productDetails)
                        } else {
                            // Ürün bulunamadı mesajı
                            Toast.makeText(requireContext(), "Product not found", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // API'den hata mesajı döndürür
                        Toast.makeText(requireContext(), "Failed to retrieve product details", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Product>, t: Throwable) {
                    // Ağ hatası veya diğer hatalar için mesaj
                    Toast.makeText(requireContext(), "An error occurred", Toast.LENGTH_SHORT).show()
                }
            })
        }

        return view
    }
}