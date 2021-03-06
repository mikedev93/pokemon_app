package com.esteban.pokemonapp.data.model

import android.os.Parcelable
import com.esteban.pokemonapp.data.pokemon.PokemonEntity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class MyTeamResponse (

    @Expose
    @SerializedName("id")
    val pokemonId: Int,

    @Expose
    @SerializedName("name")
    val chosenName: String,

    @Expose
    @SerializedName("captured_at")
    val capturedAt: String,

    @Expose
    @SerializedName("captured_lat_at")
    val capturedLatitudeAt: Double,

    @Expose
    @SerializedName("captured_long_at")
    val capturedLongitudeAt: Double,

    @Expose(serialize = false, deserialize = true)
    @SerializedName("pokemon_details")
    var pokemonDetails: PokemonEntity
) : Parcelable