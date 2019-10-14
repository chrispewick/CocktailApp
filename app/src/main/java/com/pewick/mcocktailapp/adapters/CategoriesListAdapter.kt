package com.pewick.mcocktailapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pewick.mcocktailapp.R
import com.pewick.mcocktailapp.datamodels.Category
import com.pewick.mcocktailapp.viewmodels.CategoriesViewModel

class CategoriesListAdapter(
    private val viewModel: CategoriesViewModel,
    private val categories: ArrayList<Category>): RecyclerView.Adapter<CategoriesListAdapter.CategoryVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryVH(view)
    }

    override fun getItemCount(): Int {
       return categories.size
    }

    override fun onBindViewHolder(holder: CategoryVH, position: Int) {
        holder.bind(categories[position], viewModel)
    }

    class CategoryVH(private val view: View): RecyclerView.ViewHolder(view) {
        internal var name = view.findViewById<TextView>(R.id.name)

        fun bind(item: Category, viewModel: CategoriesViewModel) {
            name.text = item.name
            view.setOnClickListener {
                viewModel.categorySelected(item.name)
            }
        }
    }
}