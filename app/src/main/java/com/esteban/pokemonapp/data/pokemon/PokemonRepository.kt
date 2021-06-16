package com.esteban.pokemonapp.data.pokemon

import android.app.Application
import androidx.lifecycle.LiveData
import com.esteban.pokemonapp.data.PokemonDatabase
import com.esteban.pokemonapp.data.model.PokemonResponse
import com.esteban.pokemonapp.network.NetworkModule

class PokemonRepository(application: Application) {

    private val pokemonDao = PokemonDatabase.getDatabase(application).pokemonDao()

    fun getPokemonFromServer(id: Int): PokemonResponse {
        return NetworkModule().providePokemonAPIService()?.getPokemonById(id.toString())!!
    }

    fun getPokemonFromDB(id: Int): LiveData<PokemonEntity> {
        return pokemonDao.getSpecificPokemon(id)
    }
}