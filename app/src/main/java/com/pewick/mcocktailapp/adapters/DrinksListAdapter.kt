package com.pewick.mcocktailapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pewick.mcocktailapp.R
import com.pewick.mcocktailapp.datamodels.Drink
import com.pewick.mcocktailapp.viewmodels.DrinksViewModel

class DrinksListAdapter(
    private val viewModel: DrinksViewModel,
    private val categories: ArrayList<Drink>): RecyclerView.Adapter<DrinksListAdapter.DrinkVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_drink, parent, false)
        return DrinkVH(view)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: DrinkVH, position: Int) {
        holder.bind(categories[position], viewModel)
    }

    class DrinkVH(private val view: View): RecyclerView.ViewHolder(view) {
        private var name = view.findViewById<TextView>(R.id.name)
        private var thumbnail = view.findViewById<ImageView>(R.id.thumbnail)

        fun bind(item: Drink, viewModel: DrinksViewModel) {
            Glide.with(thumbnail).load(item.imageUrl).into(thumbnail)
            name.text = item.name
            view.setOnClickListener {
                viewModel.drinkSelected(item.id)
            }
        }
    }
}