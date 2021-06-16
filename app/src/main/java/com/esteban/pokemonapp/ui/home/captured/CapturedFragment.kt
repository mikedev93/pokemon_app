package com.esteban.pokemonapp.ui.home.captured

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.esteban.pokemonapp.R
import com.esteban.pokemonapp.adapters.CapturedRecyclerAdapter
import com.esteban.pokemonapp.data.DataMapper
import com.esteban.pokemonapp.data.SessionManager
import com.esteban.pokemonapp.data.captured.CapturedEntity
import com.esteban.pokemonapp.data.model.PokemonResponse
import com.esteban.pokemonapp.data.pokemon.PokemonEntity
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
class CapturedFragment : Fragment(), CapturedRecyclerAdapter.CapturedOnClickListener {

    private val TAG = CapturedFragment::class.java.simpleName
    lateinit var viewModel: CapturedViewModel
    lateinit var adapter: CapturedRecyclerAdapter

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
                                    updateAdapter(list)
                                    for (item in list){
                                        viewModel.getPokemonFromDB(item.id).observe(viewLifecycleOwner,
                                            Observer<PokemonEntity> { pokemon ->
                                                if (pokemon == null) {
                                                    lifecycleScope.launch {
                                                        Log.d(TAG, "No pokemon on DB: fetching new data")
                                                        viewModel.getPokemonFromServer(item.id)
                                                    }
                                                } else {
                                                    Log.d(TAG, "Data found: loading data")
                                                    adapter.updatePokemonDetail(pokemon)
                                                }
                                            }
                                        )
                                    }
                                }
                            })
                    }
                }
            })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
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

        viewModel.getPokemon().observe(viewLifecycleOwner,
            Observer<PokemonResponse> { it ->
                lifecycleScope.launch {
                    viewModel.savePokemonToDB(DataMapper.pokemonResponseToEntity(it))
                }
            })
    }

    fun initRecyclerView(view: View) {
        var recyclerView = view.recyclerview_captured
        recyclerView.setHasFixedSize(true)
        adapter = CapturedRecyclerAdapter(view.context, ArrayList<CapturedEntity?>(), this)
        recyclerView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = adapter
    }

    fun updateAdapter(list: List<CapturedEntity>) {
        adapter.updateList(ArrayList(list))
    }

    override fun onItemClick(item: CapturedEntity) {
        Toast.makeText(context, item.name, Toast.LENGTH_SHORT).show()
    }
}