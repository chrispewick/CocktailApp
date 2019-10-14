package com.pewick.mcocktailapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pewick.mcocktailapp.R

class IngredientsListAdapter(private val ingredients: ArrayList<String>) : RecyclerView.Adapter<IngredientsListAdapter.IngredientVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient, parent, false)
        return IngredientVH(view)
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    override fun onBindViewHolder(holder: IngredientVH, position: Int) {
        holder.bind(ingredients[position])
    }

    class IngredientVH(view: View): RecyclerView.ViewHolder(view) {
        private var ingredient = view.findViewById<TextView>(R.id.ingredient)

        fun bind(item: String) {
            ingredient.text = item
        }
    }
}