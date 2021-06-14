package com.esteban.pokemonapp.data.token

import android.app.Application
import androidx.lifecycle.LiveData
import com.esteban.pokemonapp.data.Constants
import com.esteban.pokemonapp.data.PokemonDatabase
import com.esteban.pokemonapp.network.NetworkModule

class TokenRepository(application: Application) {

    private val tokenDao = PokemonDatabase.getDatabase(application).tokenDao()

    suspend fun getTokenFromServer() {
        val data = NetworkModule().provideAPIService()?.fetchToken(Constants.authorizedEmail)
        tokenDao.deleteToken()
        tokenDao.insertNewToken(TokenMapper().getLocalTokenModel(data?.body()!!))
    }

    fun getTokenFromDB(): LiveData<TokenEntity> {
        return tokenDao.getToken()
    }

}