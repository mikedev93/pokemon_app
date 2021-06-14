package com.esteban.pokemonapp.data.pokemon

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class PokemonType (

    @SerializedName("slot")
    val slot: Int,

    @SerializedName("type")
    val type: NestedType
) : Parcelable

@Parcelize
class NestedType (

    @SerializedName("type")
    val name: String,

    @SerializedName("url")
    val url: String
) : Parcelable