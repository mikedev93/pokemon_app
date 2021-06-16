package com.esteban.pokemonapp.data.model

import android.os.Parcelable
import com.esteban.pokemonapp.data.pokemon.PokemonCommon
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class PokemonResponsePaginated(
    @SerializedName("count")
    val count: Int,

    @SerializedName("next")
    val next: String,

    @SerializedName("previous")
    val previous: String,

    @SerializedName("results")
    val results: List<PokemonResults>
) : Parcelable

@Parcelize
data class PokemonResults(

    @SerializedName("name")
    val name: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("spotted_lat_at")
    var spottedLatitudeAt: Double,

    @SerializedName("spotted_long_at")
    var spottedLongitudeAt: Double,

    @SerializedName("pokemon")
    var pokemon: PokemonCommon?
) : Parcelable