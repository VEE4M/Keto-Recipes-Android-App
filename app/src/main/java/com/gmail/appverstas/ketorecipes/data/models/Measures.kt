package com.gmail.appverstas.ketorecipes.data.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Measures(
    @SerializedName("metric")
    val metric: @RawValue Metric,
    @SerializedName("us")
    val us: @RawValue Us
): Parcelable