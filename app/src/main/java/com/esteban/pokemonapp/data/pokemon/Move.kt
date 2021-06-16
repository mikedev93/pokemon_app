package com.esteban.pokemonapp.data.pokemon

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Move (

    @SerializedName("move")
    val nestedMove: NestedMove
) : Parcelable

@Parcelize
class NestedMove(
    @SerializedName("name")
    var name: String?,

    @SerializedName("level")
    var level: String?,

    @SerializedName("url")
    var url: String?
) : Parcelable