package com.esteban.pokemonapp.data.model

import com.esteban.pokemonapp.data.pokemon.Move
import com.esteban.pokemonapp.data.pokemon.PokemonType
import com.esteban.pokemonapp.data.pokemon.Sprites
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

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
    @SerializedName("sprites")
    val sprites: Sprites?,

    @Expose(serialize = false, deserialize = true)
    @SerializedName("moves")
    var moves: List<Move>?,

    @Expose(serialize = false, deserialize = true)
    @SerializedName("types")
    var types: List<PokemonType>?
)