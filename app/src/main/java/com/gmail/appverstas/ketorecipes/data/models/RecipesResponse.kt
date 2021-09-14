package com.gmail.appverstas.ketorecipes.data.models


import com.google.gson.annotations.SerializedName

data class RecipesResponse(
    @SerializedName("results")
    val list: List<Recipe>
)