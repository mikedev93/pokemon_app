package com.esteban.pokemonapp.ui.home.myTeam

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.esteban.pokemonapp.R
import com.esteban.pokemonapp.adapters.MyTeamRecyclerAdapter
import com.esteban.pokemonapp.data.Constants
import com.esteban.pokemonapp.data.DataMapper
import com.esteban.pokemonapp.data.SessionManager
import com.esteban.pokemonapp.data.model.PokemonResponse
import com.esteban.pokemonapp.data.pokemon.PokemonEntity
import com.esteban.pokemonapp.data.team.MyTeamEntity
import com.esteban.pokemonapp.data.token.TokenEntity
import com.esteban.pokemonapp.ui.detail.DetailActivity
import com.esteban.pokemonapp.utilities.Utils
import com.esteban.pokemonapp.viewmodels.MyTeamViewModel
import kotlinx.android.synthetic.main.fragment_my_team.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyTeamFragment : Fragment(), MyTeamRecyclerAdapter.OnMyTeamClickListener {

    private val TAG = MyTeamFragment::class.java.simpleName
    lateinit var viewModel: MyTeamViewModel
    lateinit var adapter: MyTeamRecyclerAdapter

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
                                    updateAdapter(list)
                                    for (item in list){
                                        viewModel.getPokemonFromDB(item.pokemonId).observe(viewLifecycleOwner,
                                            Observer<PokemonEntity> { pokemon ->
                                                if (pokemon == null) {
                                                    lifecycleScope.launch {
                                                        Log.d(TAG, "No pokemon on DB: fetching new data")
                                                        viewModel.getPokemonFromServer(item.pokemonId)
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
        viewModel.getMyTeam().observe(viewLifecycleOwner,
            Observer<List<MyTeamEntity>> { list ->
                if (!list.isNullOrEmpty()) {
                    lifecycleScope.launch {
                        viewModel.saveMyTeamList(list)
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

    private fun initRecyclerView(view: View) {
        var recyclerView = view.recyclerview_my_team
        this.adapter = MyTeamRecyclerAdapter(view.context, ArrayList<MyTeamEntity?>(), this)
        recyclerView.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = this.adapter
    }

    private fun updateAdapter(list: List<MyTeamEntity>) {
        adapter.updateList(ArrayList(list))
    }

    override fun onItemClick(item: MyTeamEntity, imageView: ImageView) {
        val detailIntent = Intent(context, DetailActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable(Constants.POKEMON, DataMapper.myTeamEntityToPokemonCommon(item))
        bundle.putString(Constants.DETAIL_ORIGIN, Constants.CAPTURED)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(context as Activity, imageView, "transition_end")
        detailIntent.putExtra(Constants.POKEMON_BUNDLE, bundle)
        startActivity(detailIntent, options.toBundle())
    }
}