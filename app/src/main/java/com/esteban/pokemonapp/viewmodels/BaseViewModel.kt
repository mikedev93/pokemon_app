package com.esteban.pokemonapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.esteban.pokemonapp.data.DataMapper
import com.esteban.pokemonapp.data.model.PokemonResponse
import com.esteban.pokemonapp.data.pokemon.PokemonEntity
import com.esteban.pokemonapp.data.pokemon.PokemonRepository
import com.esteban.pokemonapp.data.token.TokenEntity
import com.esteban.pokemonapp.data.token.TokenRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

abstract class BaseViewModel (application: Application): AndroidViewModel(application) {

    private val TAG = BaseViewModel::class.java.simpleName
    val repository = TokenRepository(application)
    val pokemonRepository = PokemonRepository(application)
    val pokemonLiveData = MutableLiveData<PokemonResponse>()

    fun getTokenFromBD(): LiveData<TokenEntity> {
        Log.d(TAG, "getTokenFromBD: fetching bd token")
        return repository.getTokenFromDB()
    }

    suspend fun getTokenFromServer() {
        Log.d(TAG, "getTokenFromServer: fetching new token")
        repository.getTokenFromServer()
    }

    fun getPokemonFromDB(id: Int): LiveData<PokemonEntity> {
        Log.d(TAG, "getPokemonFromDB: fetching bd pokemon")
        return pokemonRepository.getPokemonFromDB(id)
    }

    fun getPokemonFromServer(id: Int){
        Log.d(TAG, "getPokemonFromServer: fetching pokemon")
        pokemonRepository.getPokemonFromServer(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it -> pokemonLiveData.value = it }
    }

    suspend fun savePokemonToDB(pokemonEntity: PokemonEntity) {
        pokemonRepository.savePokemonToDB(pokemonEntity)
    }

    fun getPokemon(): MutableLiveData<PokemonResponse> {
        return pokemonLiveData
    }
}