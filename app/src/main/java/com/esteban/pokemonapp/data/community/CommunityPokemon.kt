package com.esteban.pokemonapp.data.community

import android.os.Parcelable
import com.esteban.pokemonapp.data.pokemon.PokemonEntity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class CommunityPokemon (

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("captured_at")
    val capturedAt: String,

    @Expose(serialize = false, deserialize = true)
    @SerializedName("pokemon_details")
    var pokemonDetails: PokemonEntity?
) : Parcelable