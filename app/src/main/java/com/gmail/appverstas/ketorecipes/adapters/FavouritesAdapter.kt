package com.gmail.appverstas.ketorecipes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gmail.appverstas.ketorecipes.data.local.entities.FavouritesEntity
import com.gmail.appverstas.ketorecipes.data.models.Recipe
import com.gmail.appverstas.ketorecipes.databinding.FavouritesRowLayoutBinding
import com.gmail.appverstas.ketorecipes.util.RecipesDiffUtil

/**
 *Veli-Matti Tikkanen, 8.9.2021
 */
class FavouritesAdapter : RecyclerView.Adapter<FavouritesAdapter.ViewHolder>() {

    private var listOfRecipes = emptyList<FavouritesEntity>()
    private var onItemClickListener: ((Recipe) -> Unit)? = null

    class ViewHolder(private val binding: FavouritesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favouritesEntity: FavouritesEntity) {
            binding.favouritesEntity = favouritesEntity
            RecipesRowBinding.setReadyInMinutes(
                binding.tvFavouriteTime,
                binding.favouritesEntity!!.recipe.readyInMinutes
            )
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            FavouritesRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
          holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(listOfRecipes[position].recipe)
            }
        }

        holder.bind(listOfRecipes[position])
    }

    override fun getItemCount(): Int {
        return listOfRecipes.size
    }

    fun updateFavouritesList(updatedList: List<FavouritesEntity>) {
        val recipesDiffUtil = RecipesDiffUtil(listOfRecipes, updatedList)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        listOfRecipes = updatedList
        diffUtilResult.dispatchUpdatesTo(this)
    }

    fun setOnItemClickListener(onItemClick: (Recipe) -> Unit) {
        onItemClickListener = onItemClick
    }


}


