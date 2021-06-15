package com.esteban.pokemonapp.data.community

import android.app.Application
import com.esteban.pokemonapp.data.SessionManager
import com.esteban.pokemonapp.data.model.CommunityResponse
import com.esteban.pokemonapp.network.NetworkModule
import io.reactivex.rxjava3.core.Observable

class CommunityRepository(application: Application) {

    fun getCommunityFromServer(): Observable<CommunityResponse> {
        return NetworkModule().provideAPIService()?.getCommunityActivity(SessionManager.tokenEntity?.token!!)!!
    }
}