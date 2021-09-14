package com.gmail.appverstas.ketorecipes.data.remote

import com.gmail.appverstas.ketorecipes.data.models.RecipesResponse
import retrofit2.Response
import javax.inject.Inject

/**
 *Veli-Matti Tikkanen, 26.8.2021
 */


class RemoteDataSource @Inject constructor(private val recipesApi: RecipesApi) {


    suspend fun getRecipes(queries: Map<String, String>): Response<RecipesResponse>{
        return recipesApi.getRecipes(queries)
    }

    suspend fun searchRecipes(searchQueries: Map<String, String>): Response<RecipesResponse>{
        return recipesApi.searchRecipes(searchQueries)
    }


}