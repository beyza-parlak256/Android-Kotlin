package com.beyzaparlak.trendsapp.uiFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beyzaparlak.trendsapp.ProductActivty
import com.beyzaparlak.trendsapp.R
import com.beyzaparlak.trendsapp.configs.ProductSearchViewModel
import com.beyzaparlak.trendsapp.dao.ProductAdapter
import com.beyzaparlak.trendsapp.models.Product

class ProductSearchFragment : Fragment() {
    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private val searchViewModel: ProductSearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product_search, container, false)
        searchView = view.findViewById(R.id.searchView)
        recyclerView = view.findViewById(R.id.recyclerViewProducts)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // itemClickListener tanımlıyoruz ve ProductAdapter'a geçiyoruz
        searchViewModel.products.observe(viewLifecycleOwner) { products ->
            adapter = ProductAdapter(products, object : ProductAdapter.OnItemClickListener {
                override fun onItemClick(product: Product) {
                    val intent = Intent(requireContext(), ProductActivty::class.java)
                    intent.putExtra("productId", product.id)
                    startActivity(intent)
                }
            })
            recyclerView.adapter = adapter
        }

        // SearchView için listener ve her arama işleminde çalışır
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchViewModel.getSearch(query)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                searchViewModel.getSearch(newText)
                return true
            }
        })
        return view
    }
}