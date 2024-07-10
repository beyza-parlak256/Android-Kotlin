package com.beyzaparlak.trendsapp.uiFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.beyzaparlak.trendsapp.ProductActivty
import com.beyzaparlak.trendsapp.configs.ProductViewModel
import com.beyzaparlak.trendsapp.dao.ProductAdapter
import com.beyzaparlak.trendsapp.databinding.FragmentHomeBinding
import com.beyzaparlak.trendsapp.models.Product
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val productViewModel: ProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // recycler view baglama islemi
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        // ilk 30 urun listeme islemi
        productViewModel.getProducts(limit = 30).observe(viewLifecycleOwner) { products ->
            recyclerView.adapter = ProductAdapter(products, object : ProductAdapter.OnItemClickListener {
                override fun onItemClick(product: Product) {
                    val intent = Intent(requireContext(), ProductActivty::class.java)
                    intent.putExtra("productId", product.id)
                    startActivity(intent)
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}