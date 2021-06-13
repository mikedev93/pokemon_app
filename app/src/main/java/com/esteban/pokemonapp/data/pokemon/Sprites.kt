package com.esteban.pokemonapp.data.pokemon

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Sprites (

    @SerializedName("back_default")
    val backDefault: String,

    @SerializedName("front_default")
    val frontDefault: String
): Parcelable