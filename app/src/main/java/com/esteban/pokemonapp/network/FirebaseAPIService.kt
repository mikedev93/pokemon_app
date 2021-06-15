package com.esteban.pokemonapp.network

import com.esteban.pokemonapp.data.captured.CapturedEntity
import com.esteban.pokemonapp.data.model.CommunityResponse
import com.esteban.pokemonapp.data.model.MyTeamResponse
import com.esteban.pokemonapp.data.model.TokenResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface FirebaseAPIService {

    @POST("token")
    suspend fun fetchToken(@Query("email") email: String): Response<TokenResponse>


    @GET("activity")
    fun getCommunityActivity(@Header("Authorization") token: String): Observable<CommunityResponse>

    @GET("my-team")
    fun getMyTeam(@Header("Authorization") token: String): Observable<List<MyTeamResponse>>

    @GET("captured")
    fun getCaptured(@Header("Authorization") token: String): Observable<List<CapturedEntity>>
}