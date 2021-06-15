package com.esteban.pokemonapp.data.captured

import android.app.Application
import androidx.lifecycle.LiveData
import com.esteban.pokemonapp.data.PokemonDatabase
import com.esteban.pokemonapp.data.SessionManager
import com.esteban.pokemonapp.network.NetworkModule
import io.reactivex.rxjava3.core.Observable

class CapturedRepository(application: Application) {

    val capturedDao = PokemonDatabase.getDatabase(application).capturedDao()

    fun getCapturedFromServer(): Observable<List<CapturedEntity>> {
        return NetworkModule().provideAPIService()?.getCaptured(SessionManager.tokenEntity?.token!!)!!
    }

    fun getCapturedFromDB(): LiveData<List<CapturedEntity>> {
        return capturedDao.getAllCapturedPokemons()
    }

    suspend fun saveCapturedPokemonToDB(captured: CapturedEntity) {
        capturedDao.insertNewCapturedPokemon(captured)
    }

    suspend fun saveCapturedPokemonsToDB(list: List<CapturedEntity>) {
        capturedDao.insertNewCapturedPokemonList(list)
    }
}