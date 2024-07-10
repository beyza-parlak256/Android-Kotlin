package com.beyzaparlak.trendsapp.dao

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.beyzaparlak.trendsapp.R
import com.beyzaparlak.trendsapp.databinding.ProductRowBinding
import com.beyzaparlak.trendsapp.models.Product
import com.squareup.picasso.Picasso

class ProductAdapter(private val products: List<Product>, private val itemClickListener: OnItemClickListener)
    : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(product: Product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount() = products.size

    inner class ProductViewHolder(private val binding: ProductRowBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener.onItemClick(products[position])
                }
            }
        }
        // ürün bilgileri viewlere bağlar ve görüntülememizi sağlar
        fun bind(product: Product) {
            binding.apply {
                txtTitle.text = product.title
                txtPrice.text = "${product.price}$"
                txtScore.text = product.rating.toString()
                if (product.images.isNotEmpty()) {
                    Picasso.get().load(product.images[0]).into(imgProduct)
                } else {
                    imgProduct.setImageResource(R.drawable.img_place_order)
                }
            }
        }
    }
}