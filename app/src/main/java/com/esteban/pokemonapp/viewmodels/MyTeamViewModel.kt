package com.esteban.pokemonapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.esteban.pokemonapp.data.DataMapper
import com.esteban.pokemonapp.data.team.MyTeamEntity
import com.esteban.pokemonapp.data.team.MyTeamRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MyTeamViewModel(application: Application) : BaseViewModel(application) {

    val TAG = MyTeamViewModel::class.java.simpleName
    private val myTeamRepository = MyTeamRepository(application)
    private var myTeam = MutableLiveData<List<MyTeamEntity>>()

    fun getMyTeamFromServer() {
        Log.d(TAG,"getMyTeamFromService: fetching new data")
        myTeamRepository.getMyTeamFromServer()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it -> myTeam.value = DataMapper.getLocalMyTeamListModel(it) }
    }

    fun getMyTeamFromDB():LiveData<List<MyTeamEntity>> {
        Log.d(TAG,"getMyTeamFromDB: fetching data")
        return myTeamRepository.getMyTeamFromDB()
    }

    fun getMyTeam(): MutableLiveData<List<MyTeamEntity>> {
        Log.d(TAG,"getMyTeam: MutableLiveData")
        return myTeam
    }

    suspend fun saveMyTeamList(list: List<MyTeamEntity>){
        Log.d(TAG,"saveMyTeamList: saving teamList")
        myTeamRepository.saveTeamToDB(list)
    }
}