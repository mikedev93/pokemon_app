package com.esteban.pokemonapp.ui.home.myTeam

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.esteban.pokemonapp.R
import com.esteban.pokemonapp.data.SessionManager
import com.esteban.pokemonapp.data.team.MyTeamEntity
import com.esteban.pokemonapp.data.token.TokenEntity
import com.esteban.pokemonapp.utilities.Utils
import com.esteban.pokemonapp.viewmodels.MyTeamViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_my_team.*
import kotlinx.android.synthetic.main.fragment_my_team.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyTeamFragment : Fragment() {

    private val TAG = MyTeamFragment::class.java.simpleName
    lateinit var viewModel: MyTeamViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_team, container, false)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity?.application!!)
        ).get(
            MyTeamViewModel::class.java
        )
        viewModel.getTokenFromBD().observe(this.viewLifecycleOwner,
            Observer<TokenEntity> { tokenEntity ->
                if (Utils.shouldFetchToken(tokenEntity)) {
                    GlobalScope.launch {
                        Log.d(TAG, "onChanged: fetching new token")
                        viewModel.getTokenFromServer()
                    }
                } else {
                    SessionManager.tokenEntity = tokenEntity
                    lifecycleScope.launch {
                        Log.d(TAG, "validToken: fetching data")
                        viewModel.getMyTeamFromDB().observe(viewLifecycleOwner,
                            Observer<List<MyTeamEntity>> { list ->
                                if (list.isNullOrEmpty()) {
                                    lifecycleScope.launch {
                                        Log.d(TAG, "No data on MyTeam_table: fetching new data")
                                        viewModel.getMyTeamFromServer()
                                    }
                                } else {
                                    Log.d(TAG, "Data found: loading data")
                                    view.tv_info.text = Gson().toJson(list)
                                }
                            })
                    }
                }
            })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData() {
        viewModel.getMyTeam().observe(viewLifecycleOwner,
            Observer<List<MyTeamEntity>> { list ->
                if (!list.isNullOrEmpty()) {
                    lifecycleScope.launch {
                        viewModel.saveMyTeamList(list)
                    }
                }
            })
    }
}