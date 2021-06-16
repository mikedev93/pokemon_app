package com.esteban.pokemonapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.esteban.pokemonapp.data.community.CommunityRepository
import com.esteban.pokemonapp.data.model.CommunityResponse
import com.esteban.pokemonapp.data.pokemon.PokemonRepository
import com.esteban.pokemonapp.data.token.TokenEntity
import com.esteban.pokemonapp.ui.home.community.CommunityFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class CommunityViewModel(application: Application) : BaseViewModel(application) {

    val TAG = CommunityViewModel::class.java.simpleName
    private val communityRepository = CommunityRepository(application)
    private val pokemonRepository = PokemonRepository(application)
    private var communityResponse = MutableLiveData<CommunityResponse>()

    fun getCommunity() {
        Log.d(TAG, "getCommunity: fetching new data")
        communityRepository.getCommunityFromServer()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it -> communityResponse.value = it }

    }

    fun getCommunityActivity(): MutableLiveData<CommunityResponse> {
        return communityResponse
    }
}