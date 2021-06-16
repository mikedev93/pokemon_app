package com.esteban.pokemonapp.network

import com.esteban.pokemonapp.data.model.PokemonResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonAPIService {

    @GET("pokemon/{id}")
    fun getPokemonById(@Path("id") id: String): Observable<PokemonResponse>
}