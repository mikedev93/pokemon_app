package com.esteban.pokemonapp.data.model

import androidx.room.ColumnInfo
import com.esteban.pokemonapp.data.pokemon.PokemonEntity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CapturedModel (

    @SerializedName("id")
    @ColumnInfo(name = "id")
    val pokemonId: Int,

    @SerializedName("name")
    @ColumnInfo(name = "name")
    val chosenName: String,

    @SerializedName("captured_at")
    @ColumnInfo(name = "captured_at")
    val capturedAt: String,

    @SerializedName("captured_lat_at")
    @ColumnInfo(name = "captured_lat_at")
    val capturedLatitudeAt: Double,

    @SerializedName("captured_long_at")
    @ColumnInfo(name = "captured_long_at")
    val capturedLongitudeAt: Double,

    @Expose(serialize = false, deserialize = true)
    @SerializedName("pokemon_details")
    var pokemonDetails: PokemonEntity
)