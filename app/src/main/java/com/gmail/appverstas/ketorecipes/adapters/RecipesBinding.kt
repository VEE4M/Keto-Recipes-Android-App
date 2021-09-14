package com.gmail.appverstas.ketorecipes.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.gmail.appverstas.ketorecipes.data.local.entities.RecipesEntity
import com.gmail.appverstas.ketorecipes.data.models.RecipesResponse
import com.gmail.appverstas.ketorecipes.util.NetworkResult

/**
 *Veli-Matti Tikkanen, 31.8.2021
 */
class RecipesBinding {

    companion object {

        @BindingAdapter("readApiResponse", "readDatabase", requireAll = true)
        @JvmStatic
        fun handleReadDataErrors(
            view: View,
            apiResponse: NetworkResult<RecipesResponse>?,
            listOfEntities: List<RecipesEntity>?
        ) {
            when(view){
                is ImageView -> {
                    view.isVisible = apiResponse is NetworkResult.Error && listOfEntities.isNullOrEmpty()
                }
                is TextView -> {
                    view.isVisible = apiResponse is NetworkResult.Error && listOfEntities.isNullOrEmpty()
                    view.text = apiResponse?.message.toString()
                }
            }

        }

    }
}