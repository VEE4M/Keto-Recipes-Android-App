package com.gmail.appverstas.ketorecipes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.gmail.appverstas.ketorecipes.R
import com.gmail.appverstas.ketorecipes.data.models.ExtendedIngredient
import com.gmail.appverstas.ketorecipes.util.Constants.BASE_IMAGE_URL
import com.gmail.appverstas.ketorecipes.util.RecipesDiffUtil
import kotlinx.android.synthetic.main.ingredients_row_layout.view.*

/**
 *Veli-Matti Tikkanen, 3.9.2021
 */
class IngredientsAdapter: RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    private var listOfIngredients = emptyList<ExtendedIngredient>()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ingredients_row_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.ivIngredient.load(BASE_IMAGE_URL + listOfIngredients[position].image){
            crossfade(600)
            error(R.drawable.ic_hourglass)
        }
        holder.itemView.tvIngredientTitle.text = listOfIngredients[position].name.replaceFirstChar { it.uppercase() }
        holder.itemView.tvAmount.text = listOfIngredients[position].measures.metric.amount.toString()
        holder.itemView.tvIngredientAmountUnits.text = listOfIngredients[position].measures.metric.unitShort
        holder.itemView.tvTexture.text = listOfIngredients[position].consistency ?: ""
    }

    override fun getItemCount(): Int {
        return listOfIngredients.size
    }

    fun setListOfIngredients(list: List<ExtendedIngredient>){
        val ingredientsDiffUtil = RecipesDiffUtil(listOfIngredients, list)
        val result = DiffUtil.calculateDiff(ingredientsDiffUtil)
        listOfIngredients = list
        result.dispatchUpdatesTo(this)
    }


}