package com.esteban.pokemonapp.network

import androidx.lifecycle.LiveData
import com.esteban.pokemonapp.data.model.TokenResponse
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface FirebaseAPIService {

    @POST("token")
    suspend fun fetchToken(@Query("email") email: String): Response<TokenResponse>

}