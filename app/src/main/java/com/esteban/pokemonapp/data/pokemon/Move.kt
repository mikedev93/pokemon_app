package com.esteban.pokemonapp.data.pokemon

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Move (

    @SerializedName("name")
    val name: String,

    @SerializedName("url")
    val url: String
) : Parcelable