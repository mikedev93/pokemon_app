package com.esteban.pokemonapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.esteban.pokemonapp.data.token.TokenEntity
import com.esteban.pokemonapp.data.token.TokenRepository

class ExploreViewModel(application: Application) : AndroidViewModel(application) {

    val repository = TokenRepository(application)

    fun getTokenFromBD(): LiveData<TokenEntity> {
        return repository.getTokenFromDB()
    }

    suspend fun getTokenFromServer() {
        repository.getTokenFromServer()
    }
}