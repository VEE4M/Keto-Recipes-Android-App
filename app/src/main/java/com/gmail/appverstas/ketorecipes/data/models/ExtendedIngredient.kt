package com.gmail.appverstas.ketorecipes.data.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class ExtendedIngredient(
    @SerializedName("consistency")
    val consistency: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String?,
    @SerializedName("measures")
    val measures: @RawValue Measures,
    @SerializedName("name")
    val name: String
): Parcelable