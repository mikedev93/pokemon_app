package com.esteban.pokemonapp.data.model

import android.os.Parcelable
import com.esteban.pokemonapp.data.pokemon.Move
import com.esteban.pokemonapp.data.pokemon.PokemonType
import com.esteban.pokemonapp.data.pokemon.Sprites
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
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
) : Parcelable