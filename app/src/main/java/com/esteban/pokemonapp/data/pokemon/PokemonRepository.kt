package com.esteban.pokemonapp.data.pokemon

import android.app.Application
import androidx.lifecycle.LiveData
import com.esteban.pokemonapp.data.PokemonDatabase
import com.esteban.pokemonapp.data.model.PokemonResponse
import com.esteban.pokemonapp.data.model.PokemonResponsePaginated
import com.esteban.pokemonapp.network.NetworkModule
import io.reactivex.rxjava3.core.Observable

class PokemonRepository(application: Application) {

    private val pokemonDao = PokemonDatabase.getDatabase(application).pokemonDao()

    fun getPokemonFromServer(id: Int): Observable<PokemonResponse> {
        return NetworkModule().providePokemonAPIService()?.getPokemonById(id.toString())!!
    }

    fun getPokemonFromServerByName(name: String): Observable<PokemonResponse> {
        return NetworkModule().providePokemonAPIService()?.getPokemonById(name)!!
    }

    fun getPokemonFromDB(id: Int): LiveData<PokemonEntity> {
        return pokemonDao.getSpecificPokemon(id)
    }

    fun getPokemonFromDBByName(name: String): LiveData<PokemonEntity> {
        return pokemonDao.getPokemonByName(name)
    }

    suspend fun savePokemonToDB(pokemonEntity: PokemonEntity) {
        pokemonDao.insertNewPokemon(pokemonEntity)
    }

    fun getRandomPokemons(): Observable<PokemonResponsePaginated>? {
        return NetworkModule().providePokemonAPIService()?.getPokemons()
    }
}