package com.gmail.appverstas.ketorecipes.data.local

import androidx.room.*
import com.gmail.appverstas.ketorecipes.data.local.entities.FavouritesEntity
import com.gmail.appverstas.ketorecipes.data.local.entities.RecipesEntity
import com.gmail.appverstas.ketorecipes.util.Constants.FAVOURITES_TABLE
import com.gmail.appverstas.ketorecipes.util.Constants.RECIPES_TABLE
import kotlinx.coroutines.flow.Flow

/**
 *Veli-Matti Tikkanen, 27.8.2021
 */
@Dao
interface KetoRecipesDao {

    @Query("SELECT * FROM $RECIPES_TABLE ORDER BY id ASC")
    fun getRecipes(): Flow<List<RecipesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipesEntity: RecipesEntity)

    @Delete
    suspend fun deleteRecipe(recipesEntity: RecipesEntity)

    @Query("SELECT * FROM $FAVOURITES_TABLE ORDER BY id ASC")
    fun getFavourites(): Flow<List<FavouritesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(favouritesEntity: FavouritesEntity)

    @Delete
    suspend fun deleteFavourite(favouritesEntity: FavouritesEntity)

}