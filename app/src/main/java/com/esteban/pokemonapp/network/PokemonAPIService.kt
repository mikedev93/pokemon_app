package com.esteban.pokemonapp.network

import com.esteban.pokemonapp.data.model.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonAPIService {

    @GET("{id]")
    fun getPokemonById(@Path("id") id: String): PokemonResponse
}