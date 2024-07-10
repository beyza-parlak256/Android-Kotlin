package com.beyzaparlak.trendsapp.dao

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.beyzaparlak.trendsapp.R
import com.beyzaparlak.trendsapp.models.Category

class CategoryAdapter(private val context: Activity, private val categories: List<Category>)
    : ArrayAdapter<Category>(context, R.layout.category_row, categories) {

        // kategorilerdeki listeyi gösteriyor. her kategori için rowView döner
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.category_row, null, true)

        val categoryName = rowView.findViewById<TextView>(R.id.categoryName)
        val category = categories[position]
        categoryName.text = category.name

        return rowView
    }
}
