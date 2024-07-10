package com.beyzaparlak.trendsapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.beyzaparlak.trendsapp.configs.CartResponse
import com.beyzaparlak.trendsapp.configs.RetrofitClient
import com.beyzaparlak.trendsapp.models.Cart
import com.beyzaparlak.trendsapp.models.CartItem
import com.beyzaparlak.trendsapp.models.Product
import com.beyzaparlak.trendsapp.uiFragment.OrdersFragment
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductActivty : AppCompatActivity() {

    private lateinit var listViewProducts: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_activty2)

        val imgProduct: ImageView = findViewById(R.id.imgProduct)
        val txtTitle: TextView = findViewById(R.id.txtTitle)
        val txtPrice: TextView = findViewById(R.id.txtPrice)
        val txtRating: TextView = findViewById(R.id.txtRating)
        val btnPlaceOrder: Button = findViewById(R.id.btnPlaceOrder)

        val productId = intent.getIntExtra("productId", -1)
        if (productId == -1) {
            finish()
            return
        }

        // ürün detaylarını getirmek için api çağrısı
        RetrofitClient.apiService.getProduct(productId).enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful) {
                    product = response.body() ?: return
                    txtTitle.text = product.title
                    txtPrice.text = "${product.price}$"
                    txtRating.text = "Rating: ${product.rating}"
                    if (product.images.isNotEmpty()) {
                        Picasso.get().load(product.images[0]).into(imgProduct)
                    } else {
                        imgProduct.setImageResource(R.drawable.img_place_order)
                    }
                } else {
                    Toast.makeText(this@ProductActivty, "Failed to retrieve product details", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                Toast.makeText(this@ProductActivty, "An error occurred", Toast.LENGTH_SHORT).show()
            }
        })

        // btnPlaceOrder butonunun tıklama olayı
        btnPlaceOrder.setOnClickListener {
            // Ürün detaylarını ListView'e ekliyoruz
            val productDetails = "${product.title} - ${product.price}$"
            adapter.add(productDetails)

            // Ürün bilgilerini sunucuya gönderir
            val cartItem = CartItem(productId = product.id, quantity = 1)
            val cart = Cart(userId = 5, products = listOf(cartItem))
            RetrofitClient.apiService.addToCart(5, cart).enqueue(object : Callback<CartResponse> {
                override fun onResponse(call: Call<CartResponse>, response: Response<CartResponse>) {
                    if (response.isSuccessful) {
                        // Ürün eklendi
                        Toast.makeText(this@ProductActivty, "Product added to cart", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@ProductActivty, "Failed to add product to cart", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                    Toast.makeText(this@ProductActivty, "An error occurred", Toast.LENGTH_SHORT).show()
                }
            })

            // OrdersFragment'a geçiş yapar
            val fragment = OrdersFragment.newInstance(productId)
            supportFragmentManager.beginTransaction()
                .replace(R.id.main, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}