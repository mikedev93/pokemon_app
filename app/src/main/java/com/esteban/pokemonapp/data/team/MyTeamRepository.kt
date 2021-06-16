package com.esteban.pokemonapp.data.team

import android.app.Application
import androidx.lifecycle.LiveData
import com.esteban.pokemonapp.data.PokemonDatabase
import com.esteban.pokemonapp.data.SessionManager
import com.esteban.pokemonapp.data.model.MyTeamResponse
import com.esteban.pokemonapp.network.NetworkModule
import io.reactivex.rxjava3.core.Observable

class MyTeamRepository(application: Application) {

    private val myTeamDao: MyTeamDao = PokemonDatabase.getDatabase(application).myTeamDao()

    fun getMyTeamFromServer(): Observable<List<MyTeamResponse>> {
        return NetworkModule().provideAPIService()?.getMyTeam(SessionManager.tokenEntity?.token!!)!!
    }

    fun getMyTeamFromDB(): LiveData<List<MyTeamEntity>> {
        return myTeamDao.getAllTeamMembers()
    }

    suspend fun saveTeamToDB(list: List<MyTeamEntity>) {
        myTeamDao.insertNewTeamMemberList(list)
    }
}