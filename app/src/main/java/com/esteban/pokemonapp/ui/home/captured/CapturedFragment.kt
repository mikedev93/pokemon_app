package com.esteban.pokemonapp.ui.home.captured

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
import com.esteban.pokemonapp.data.captured.CapturedEntity
import com.esteban.pokemonapp.data.team.MyTeamEntity
import com.esteban.pokemonapp.data.token.TokenEntity
import com.esteban.pokemonapp.utilities.Utils
import com.esteban.pokemonapp.viewmodels.CapturedViewModel
import com.esteban.pokemonapp.viewmodels.MyTeamViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_captured.view.*
import kotlinx.android.synthetic.main.fragment_my_team.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CapturedFragment : Fragment() {

    private val TAG = CapturedFragment::class.java.simpleName
    lateinit var viewModel: CapturedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_captured, container, false)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity?.application!!)
        ).get(
            CapturedViewModel::class.java
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
                        viewModel.getCapturedPokemonsFromDB().observe(viewLifecycleOwner,
                            Observer<List<CapturedEntity>> { list ->
                                if (list.isNullOrEmpty()) {
                                    lifecycleScope.launch {
                                        Log.d(TAG, "No data on Captured_table: fetching new data")
                                        viewModel.getCapturedPokemonsFromServer()
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
        viewModel.getCapturedPokemons().observe(viewLifecycleOwner,
            Observer<List<CapturedEntity>> { list ->
                if (!list.isNullOrEmpty()) {
                    lifecycleScope.launch {
                        viewModel.saveCapturedPokemonList(list)
                    }
                }
            })
    }
}