package com.esteban.pokemonapp.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.esteban.pokemonapp.data.DataMapper
import com.esteban.pokemonapp.data.captured.CapturedEntity
import com.esteban.pokemonapp.data.captured.CapturedRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class CapturedViewModel(application: Application) : BaseViewModel(application) {

    val TAG = CapturedViewModel::class.java.simpleName
    private val capturedRepository = CapturedRepository(application)
    private var capturedPokemons = MutableLiveData<List<CapturedEntity>>()

    fun getCapturedPokemonsFromServer() {
        capturedRepository.getCapturedFromServer()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it -> capturedPokemons.value = it }
    }

    fun getCapturedPokemonsFromDB(): LiveData<List<CapturedEntity>> {
        return capturedRepository.getCapturedFromDB()
    }

    fun getCapturedPokemons(): MutableLiveData<List<CapturedEntity>>{
        return capturedPokemons
    }

    suspend fun saveCapturedPokemonList(list: List<CapturedEntity>) {
        capturedRepository.saveCapturedPokemonsToDB(list)
    }

    suspend fun saveCapturedPokemon(capturedEntity: CapturedEntity) {
        capturedRepository.saveCapturedPokemonToDB(capturedEntity)
    }
}