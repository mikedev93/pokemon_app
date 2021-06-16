package com.esteban.pokemonapp.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.esteban.pokemonapp.R
import com.esteban.pokemonapp.adapters.MovesRecyclerAdapter
import com.esteban.pokemonapp.data.Constants
import com.esteban.pokemonapp.data.DataMapper
import com.esteban.pokemonapp.data.community.Community
import com.esteban.pokemonapp.data.model.PokemonResponse
import com.esteban.pokemonapp.data.model.PokemonResults
import com.esteban.pokemonapp.data.pokemon.Move
import com.esteban.pokemonapp.data.pokemon.PokemonCommon
import com.esteban.pokemonapp.data.pokemon.PokemonEntity
import com.esteban.pokemonapp.ui.home.community.CommunityFragment
import com.esteban.pokemonapp.utilities.AppBarStateChangedListener
import com.esteban.pokemonapp.viewmodels.ExploreViewModel
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.layout_scrollable_content.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val TAG = DetailActivity::class.java.simpleName
    private val PERCENTAGE_TO_SHOW_IMAGE = 20
    private var mMaxScrollSize = 0
    private var mIsImageHidden = false
    lateinit var viewModel: ExploreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(findViewById(R.id.toolbar))
        val bundle = intent.getBundleExtra(Constants.POKEMON_BUNDLE)
        if (bundle != null) {
            viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application!!)).get(ExploreViewModel::class.java)
            when(bundle.getString(Constants.DETAIL_ORIGIN)) {
                Constants.WILD -> {
                    var pokemonResult = bundle.getParcelable<PokemonResults>(Constants.POKEMON)
                    viewModel.getPokemonFromDBByName(pokemonResult?.name!!).observe(this,
                        Observer<PokemonEntity> { pokemon ->
                            if (pokemon == null) {
                                lifecycleScope.launch {
                                    Log.d(TAG, "No pokemon on DB: fetching new data")
                                    viewModel.getPokemonFromServerByName(pokemonResult?.name!!)
                                }
                            } else {
                                Log.d(TAG, "Data found: loading data")
                                setupWildView(DataMapper.pokemonResponseToPokemonCommon(pokemonResult, pokemon))
                            }
                        }
                    )
                    viewModel.getPokemonByName(pokemonResult?.name!!)
                }
                Constants.CAPTURED_BY_OTHER -> {
                    var community = bundle.getParcelable<Community>(Constants.POKEMON)
                    setupCapturedByOtherView(community)
                }
                Constants.CAPTURED -> {
                    var captured = bundle.getParcelable<PokemonCommon>(Constants.POKEMON)
                    setupCapturedView(captured)
                }
            }
            observe()
        }
    }

    private fun observe(){
        viewModel.getPokemonByName().observe(this,
            Observer<PokemonResponse> { it ->
                lifecycleScope.launch {
                    viewModel.savePokemonToDB(DataMapper.pokemonResponseToEntity(it))
                }
            })
    }

    private fun setupWildView(pokemon: PokemonCommon) {
        setupToolbar(pokemon?.name!!, pokemon.pokemonDetails?.sprites?.frontDefault!!)
        textview_captured_at.text = pokemon.capturedAt
        setupTypes(pokemon.pokemonDetails!!)
        setupMoves(pokemon.pokemonDetails?.moves!!)
        button_capture.visibility = View.VISIBLE
        linear_captured_info.visibility = View.GONE
    }

    private fun setupCapturedView(pokemon: PokemonCommon?) {
        fab_captured.visibility = View.VISIBLE
        setupToolbar(pokemon?.name!!, pokemon.pokemonDetails?.sprites?.frontDefault!!)
        textview_captured_at.text = pokemon.capturedAt
        setupTypes(pokemon.pokemonDetails!!)
        setupMoves(pokemon.pokemonDetails?.moves!!)

        appbar.addOnOffsetChangedListener(object : AppBarStateChangedListener(){
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                when(state) {
                    State.COLLAPSED -> {
                        ViewCompat.animate(fab_captured_toolbar).scaleY(1f).scaleX(1f).start()
                        fab_captured_toolbar.visibility = View.VISIBLE
                    }
                    State.EXPANDED -> {
                        ViewCompat.animate(fab_captured_toolbar).scaleY(0f).scaleX(0f).start()
                        fab_captured_toolbar.visibility = View.GONE
                    }
                    State.IDLE -> {
                        ViewCompat.animate(fab_captured_toolbar).scaleY(0f).scaleX(0f).start()
                        fab_captured_toolbar.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun setupCapturedByOtherView(community: Community?) {
        setupToolbar(community?.pokemon?.name!!, community.pokemon.pokemonDetails?.sprites?.frontDefault!!)
        setupTrainer(community.name)
        textview_captured_at.text = community.pokemon.capturedAt
        setupTypes(community.pokemon.pokemonDetails!!)
        setupMoves(community.pokemon.pokemonDetails?.moves!!)
    }

    private fun setupTypes(pokemonEntity: PokemonEntity){
        var types = ""
        pokemonEntity.typeSlots?.forEachIndexed {index, pokemonType ->
            when(index) {
                0 -> types = pokemonType.nestedType.name.toLowerCase().capitalize()
                else -> types = "$types, ${pokemonType.nestedType.name.capitalize()}"
            }
        }
        textview_types.text = types
    }

    private fun setupMoves(moves: List<Move>){
        var recyclerView = recyclerview_moves
        recyclerView.setHasFixedSize(true)
        var adapter = MovesRecyclerAdapter(ArrayList(moves))
        recyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
    }

    private fun setupTrainer(name: String) {
        linear_trainer_info.visibility = View.VISIBLE
        textview_captured_by.text = "Captured by ${name.toLowerCase().capitalize()}"
    }

    fun setupLocation(latitude: Double, longitude: Double) {

    }

    private fun setupToolbar(pokemonName: String, imageUrl: String) {
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)
        collapsing_toolbar.title = pokemonName.toLowerCase().capitalize()
        Glide.with(this)
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(pokemonAvatar)
        collapsing_toolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar)
        collapsing_toolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar)
    }


}