package com.esteban.pokemonapp.ui.home.explore

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.esteban.pokemonapp.R
import com.esteban.pokemonapp.data.Constants
import com.esteban.pokemonapp.data.model.PokemonResponsePaginated
import com.esteban.pokemonapp.data.model.PokemonResults
import com.esteban.pokemonapp.ui.detail.DetailActivity
import com.esteban.pokemonapp.ui.home.HomeActivity
import com.esteban.pokemonapp.utilities.Utils
import com.esteban.pokemonapp.viewmodels.ExploreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_explore.*
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

@AndroidEntryPoint
class ExploreFragment : Fragment() {

    lateinit var homeActivity: HomeActivity
    lateinit var map: MapView
    lateinit var mapController: IMapController
    private lateinit var mLocationOverlay: MyLocationNewOverlay
    lateinit var viewModel: ExploreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeActivity = activity as HomeActivity
        Configuration.getInstance().load(context, activity?.getPreferences(Context.MODE_PRIVATE))
        val view = inflater.inflate(R.layout.fragment_explore, container, false)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(activity?.application!!)).get(ExploreViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        map = mapView
        mapController = map.controller
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            createLocationOverlay(mapView, mapController)
            // Set user agent
            Configuration.getInstance().userAgentValue = "PokeMaps"
            viewModel.getRandomPokemonsFromServer()
            observeData()
        } else {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
    }

    fun createLocationOverlay(map: MapView, mapController: IMapController) {
        // Create location overlay
        mLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), map)
        mLocationOverlay.enableMyLocation()
        mLocationOverlay.enableFollowLocation()
        mLocationOverlay.isDrawAccuracyEnabled = true
        mLocationOverlay.runOnFirstFix {
            activity?.runOnUiThread {
                mapController.animateTo(mLocationOverlay.myLocation)
                mapController.setZoom(17.0)
                map.setMultiTouchControls(true)
                map.isTilesScaledToDpi = true
            }
        }
        map.overlays.add(mLocationOverlay)
        var onTouchEventListener = object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (event?.action == MotionEvent.ACTION_MOVE) {
                    view?.parent?.requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        }
        map.setOnTouchListener(onTouchEventListener)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                        createLocationOverlay(mapView, mapController)
                        Configuration.getInstance().userAgentValue = "PokeMaps"
                        viewModel.getRandomPokemonsFromServer()
                        observeData()
                    }
                } else {
                    requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
                }
                return
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (map != null) {
            map.onResume()
        }
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    fun addItemsToMap(pokemons: List<PokemonResults>) {

        var currentLocation = mLocationOverlay.myLocation
        var markerList = ArrayList<Marker>()
        for (item in pokemons) {
            var point = Utils.getLocationInLatLngRad(1000.0, currentLocation)
            if (point != null) {
                item.spottedLatitudeAt = point.latitude
                item.spottedLongitudeAt = point.longitude
                var marker = Marker(map)
                marker.position = point
                marker.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_pokeball_marker)
                marker.setOnMarkerClickListener(object : Marker.OnMarkerClickListener {
                    override fun onMarkerClick(marker: Marker?, mapView: MapView?): Boolean {
                        var detailIntent = Intent(context, DetailActivity::class.java)
                        val bundle = Bundle()
                        bundle.putParcelable(Constants.POKEMON, item)
                        bundle.putString(Constants.DETAIL_ORIGIN, Constants.WILD)
                        detailIntent.putExtra(Constants.POKEMON_BUNDLE, bundle)
                        startActivity(detailIntent)
                        return false
                    }
                })
                markerList.add(marker)
            }
        }
        map.overlays.addAll(markerList)
        map.invalidate()
    }

    private fun observeData() {
        viewModel.getRandomPokemons().observe(viewLifecycleOwner,
        Observer<PokemonResponsePaginated> { it ->
            addItemsToMap(it.results)
        })
    }
}