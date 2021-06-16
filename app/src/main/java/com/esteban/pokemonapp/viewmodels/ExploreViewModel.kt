package com.esteban.pokemonapp.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.esteban.pokemonapp.data.model.PokemonResponse
import com.esteban.pokemonapp.data.model.PokemonResponsePaginated
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ExploreViewModel(application: Application) : BaseViewModel(application) {

    private var pokemonResponsePaginated = MutableLiveData<PokemonResponsePaginated>()
    private var pokemonResponse = MutableLiveData<PokemonResponse>()

    fun getRandomPokemonsFromServer() {
        pokemonRepository.getRandomPokemons()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe { it -> pokemonResponsePaginated.value = it }
    }

    fun getPokemonByName(name: String) {
        pokemonRepository.getPokemonFromServerByName(name)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe { it -> pokemonResponse.value = it }
    }

    fun getRandomPokemons(): MutableLiveData<PokemonResponsePaginated> {
        return pokemonResponsePaginated
    }

    fun getPokemonByName(): MutableLiveData<PokemonResponse> {
        return pokemonResponse
    }
}