package com.gmail.appverstas.ketorecipes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gmail.appverstas.ketorecipes.data.models.Recipe
import com.gmail.appverstas.ketorecipes.databinding.RecipesRowLayoutBinding
import com.gmail.appverstas.ketorecipes.util.RecipesDiffUtil

/**
 *Veli-Matti Tikkanen, 27.8.2021
 */
class RecipesAdapter: RecyclerView.Adapter<RecipesAdapter.ViewHolder>() {

    private var listOfRecipes = emptyList<Recipe>()

    private var onItemClickListener: ((Recipe) -> Unit)? = null

    class ViewHolder(private val binding: RecipesRowLayoutBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(recipe: Recipe){
            binding.result = recipe
            RecipesRowBinding.setReadyInMinutes(binding.tvTime, binding.result!!.readyInMinutes)
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecipesRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(listOfRecipes[position])
            }
        }
        holder.bind(listOfRecipes[position])
    }

    override fun getItemCount(): Int {
        return listOfRecipes.size
    }

    fun updateRecipesList(updatedList: List<Recipe>){
        val recipesDiffUtil = RecipesDiffUtil(listOfRecipes, updatedList)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        listOfRecipes = updatedList
        diffUtilResult.dispatchUpdatesTo(this)
    }

    fun setOnItemClickListener(onItemClick: (Recipe) -> Unit){
        onItemClickListener = onItemClick
    }


}