package com.esteban.pokemonapp.data.model

import com.esteban.pokemonapp.data.pokemon.Move
import com.esteban.pokemonapp.data.pokemon.PokemonType
import com.esteban.pokemonapp.data.pokemon.Sprites
import com.google.gson.annotations.SerializedName

class PokemonResponse (
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("sprites")
    val sprites: Sprites,

    @SerializedName("moves")
    val moves: List<Move>,

    @SerializedName("types")
    val types: List<PokemonType>
)