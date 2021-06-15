package com.esteban.pokemonapp.data.community

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Community (

    @SerializedName("name")
    val name: String,

    @SerializedName("pokemon")
    val pokemon: CommunityPokemon
) : Parcelable