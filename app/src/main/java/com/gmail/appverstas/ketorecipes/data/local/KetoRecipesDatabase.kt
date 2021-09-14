package com.gmail.appverstas.ketorecipes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gmail.appverstas.ketorecipes.data.local.entities.FavouritesEntity
import com.gmail.appverstas.ketorecipes.data.local.entities.RecipesEntity

/**
 *Veli-Matti Tikkanen, 27.8.2021
 */

@Database(entities = [RecipesEntity::class, FavouritesEntity::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class KetoRecipesDatabase: RoomDatabase() {

    abstract fun ketoRecipesDao(): KetoRecipesDao


}