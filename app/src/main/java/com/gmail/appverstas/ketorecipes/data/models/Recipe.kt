package com.gmail.appverstas.ketorecipes.data.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Recipe(
    @SerializedName("aggregateLikes")
    val aggregateLikes: Int,
    @SerializedName("extendedIngredients")
    val extendedIngredients: @RawValue List<ExtendedIngredient>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("readyInMinutes")
    val readyInMinutes: Int,
    @SerializedName("sourceUrl")
    val sourceUrl: String,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("title")
    val title: String
): Parcelable