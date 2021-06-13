package com.esteban.pokemonapp.data.team

import android.app.Application
import com.esteban.pokemonapp.data.PokemonDatabase

class MyTeamRepository(application: Application) {

    private var database = PokemonDatabase.getDatabase(application)
    private var myTeamDao: MyTeamDao = database.myTeamDao()
    private var myTeam = myTeamDao.getAllTeamMembers()
}