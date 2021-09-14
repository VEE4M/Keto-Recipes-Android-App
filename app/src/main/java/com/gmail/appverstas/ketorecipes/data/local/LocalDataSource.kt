package com.gmail.appverstas.ketorecipes.data.local

import com.gmail.appverstas.ketorecipes.data.local.entities.FavouritesEntity
import com.gmail.appverstas.ketorecipes.data.local.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *Veli-Matti Tikkanen, 30.8.2021
 */
class LocalDataSource @Inject constructor(private val ketoRecipesDao: KetoRecipesDao) {


    fun getRecipes(): Flow<List<RecipesEntity>> {
        return ketoRecipesDao.getRecipes()
    }

    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        ketoRecipesDao.insertRecipe(recipesEntity)
    }


    fun getFavourites(): Flow<List<FavouritesEntity>> {
        return ketoRecipesDao.getFavourites()
    }

    suspend fun insertFavourite(favouritesEntity: FavouritesEntity) {
        ketoRecipesDao.insertFavourite(favouritesEntity)
    }

    suspend fun deleteFavourite(favouritesEntity: FavouritesEntity) {
        ketoRecipesDao.deleteFavourite(favouritesEntity)
    }


}