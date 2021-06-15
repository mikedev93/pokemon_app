package com.esteban.pokemonapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.esteban.pokemonapp.data.token.TokenEntity
import com.esteban.pokemonapp.data.token.TokenRepository

abstract class BaseViewModel (application: Application): AndroidViewModel(application) {

    private val TAG = BaseViewModel::class.java.simpleName
    val repository = TokenRepository(application)

    fun getTokenFromBD(): LiveData<TokenEntity> {
        Log.d(TAG, "getTokenFromBD: fetching bd token")
        return repository.getTokenFromDB()
    }

    suspend fun getTokenFromServer() {
        Log.d(TAG, "getTokenFromServer: fetching new token")
        repository.getTokenFromServer()
    }
}