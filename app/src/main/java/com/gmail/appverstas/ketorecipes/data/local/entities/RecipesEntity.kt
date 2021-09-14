package com.gmail.appverstas.ketorecipes.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gmail.appverstas.ketorecipes.data.models.RecipesResponse
import com.gmail.appverstas.ketorecipes.util.Constants.RECIPES_TABLE

/**
 *Veli-Matti Tikkanen, 27.8.2021
 */

@Entity(tableName = RECIPES_TABLE)
data class RecipesEntity (
    var recipesResponse: RecipesResponse
    ){
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}