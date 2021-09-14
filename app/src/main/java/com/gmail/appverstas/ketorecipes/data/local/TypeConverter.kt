package com.gmail.appverstas.ketorecipes.data.local

import androidx.room.TypeConverter
import com.gmail.appverstas.ketorecipes.data.models.Recipe
import com.gmail.appverstas.ketorecipes.data.models.RecipesResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 *Veli-Matti Tikkanen, 30.8.2021
 */
class TypeConverter {

    val gson = Gson()

    @TypeConverter
    fun recipesResponseToString(recipesResponse: RecipesResponse): String {
        return gson.toJson(recipesResponse)
    }

    @TypeConverter
    fun stringToRecipesResponse(data: String): RecipesResponse {
        val listType = object: TypeToken<RecipesResponse>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun recipeToString(recipe: Recipe): String {
        return gson.toJson(recipe)
    }

    @TypeConverter
    fun stringToRecipe(data: String): Recipe {
        val listType = object: TypeToken<Recipe>() {}.type
        return gson.fromJson(data, listType)
    }

}