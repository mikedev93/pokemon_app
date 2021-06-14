package com.esteban.pokemonapp.data.token

import com.esteban.pokemonapp.data.Constants
import com.esteban.pokemonapp.network.BaseDataSource
import com.esteban.pokemonapp.network.FirebaseAPIService
import javax.inject.Inject

class TokenDataSource @Inject constructor(
    private val tokenDao: TokenDao,
    private val firebaseAPIService: FirebaseAPIService
): BaseDataSource() {
    internal suspend fun getData() = getData { firebaseAPIService.fetchToken(Constants.authorizedEmail)}
}