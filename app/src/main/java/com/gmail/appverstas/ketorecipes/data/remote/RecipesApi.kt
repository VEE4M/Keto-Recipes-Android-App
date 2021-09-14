package com.gmail.appverstas.ketorecipes.data.remote

import com.gmail.appverstas.ketorecipes.data.models.RecipesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 *Veli-Matti Tikkanen, 26.8.2021
 */


interface RecipesApi {

    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
        @QueryMap queries: Map<String, String>
    ): Response<RecipesResponse>


    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(
        @QueryMap searchQuery: Map<String, String>
    ): Response<RecipesResponse>

}