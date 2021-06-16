package com.esteban.pokemonapp.data.pokemon

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class TypeSlot (
    @SerializedName("slot")
    val slot: Int,

    @SerializedName("type")
    val nestedType: NestedType
) : Parcelable

@Parcelize
class NestedType (

    @SerializedName("name")
    val name: String,

    @SerializedName("url")
    val url: String
) : Parcelable