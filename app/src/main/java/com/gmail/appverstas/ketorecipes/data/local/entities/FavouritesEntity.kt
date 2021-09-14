package com.gmail.appverstas.ketorecipes.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gmail.appverstas.ketorecipes.data.models.Recipe
import com.gmail.appverstas.ketorecipes.util.Constants.FAVOURITES_TABLE

/**
 *Veli-Matti Tikkanen, 5.9.2021
 */

@Entity(tableName = FAVOURITES_TABLE )
data class FavouritesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val recipe: Recipe)