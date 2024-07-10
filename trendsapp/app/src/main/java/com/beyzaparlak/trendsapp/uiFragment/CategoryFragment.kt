package com.beyzaparlak.trendsapp.uiFragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.beyzaparlak.trendsapp.R
import com.beyzaparlak.trendsapp.configs.RetrofitClient
import com.beyzaparlak.trendsapp.dao.CategoryAdapter
import com.beyzaparlak.trendsapp.models.Category
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryFragment : Fragment() {
    private lateinit var listView: ListView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listView = view.findViewById(R.id.listViewCategories)
        val apiService = RetrofitClient.apiService

        // Verileri ListView'e adaptör aracılığıyla yükler
        apiService.getCategory().enqueue(object : Callback<List<Category>> {
            override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                if (response.isSuccessful) {
                    val categories = response.body() ?: emptyList()
                    val adapter = CategoryAdapter(requireActivity(), categories)
                    listView.adapter = adapter
                    Log.d("datas", categories.toString())

                    // Kategoriye tıklama olayını ayarlar
                    listView.setOnItemClickListener { _, _, position, _ ->
                        val selectedCategory = categories[position].name
                        val bundle = Bundle().apply {
                            putString("categoryName", selectedCategory)
                        }
                        findNavController().navigate(R.id.action_categoryFragment_to_categoryDetailsFragment, bundle)
                    }
                } else {
                    Log.e("getCategories", "Response not successful")
                }
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                Log.e("getCategories", t.message ?: "Unknown error")
            }
        })
    }
}
