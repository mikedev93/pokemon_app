package com.esteban.pokemonapp.ui.home.community

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esteban.pokemonapp.R
import com.esteban.pokemonapp.adapters.CommunityRecyclerAdapter
import com.esteban.pokemonapp.data.SessionManager
import com.esteban.pokemonapp.data.community.Community
import com.esteban.pokemonapp.data.model.CommunityResponse
import com.esteban.pokemonapp.data.token.TokenEntity
import com.esteban.pokemonapp.utilities.Utils
import com.esteban.pokemonapp.viewmodels.CommunityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_community.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommunityFragment : Fragment(), CommunityRecyclerAdapter.OnCommunityClickListener {

    private val TAG = CommunityFragment::class.java.simpleName
    lateinit var viewModel: CommunityViewModel
    lateinit var adapterFriends: CommunityRecyclerAdapter
    lateinit var adapterFoes: CommunityRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_community, container, false)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity?.application!!)
        ).get(CommunityViewModel::class.java)
        viewModel.getTokenFromBD().observe(this.viewLifecycleOwner,
            Observer<TokenEntity> { tokenEntity ->
                if (Utils.shouldFetchToken(tokenEntity)) {
                    GlobalScope.launch {
                        Log.d(TAG, "onChanged: fetching new token")
                        viewModel.getTokenFromServer()
                    }
                } else {
                    SessionManager.tokenEntity = tokenEntity
                    viewModel.getCommunity()
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
        viewModel.getCommunityActivity().observe(viewLifecycleOwner,
            Observer<CommunityResponse> { it -> updateAdapter(it) })
    }

    private fun initRecyclerView(view: View) {

        var onItemTouchListener = object : RecyclerView.OnItemTouchListener{
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                //Nothing here
            }

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                when(e.action) {
                    MotionEvent.ACTION_DOWN -> {
                        rv.parent?.requestDisallowInterceptTouchEvent(true)
                    }
                }
                return false
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
                //Nothing here
            }
        }

        var recyclerViewFriends = view.recyclerview_friends
        adapterFriends = CommunityRecyclerAdapter(view.context, ArrayList<Community?>(), this)
        recyclerViewFriends.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewFriends.adapter = adapterFriends
        recyclerViewFriends.addOnItemTouchListener(onItemTouchListener)

        var recyclerViewFoes = view.recyclerview_foes
        adapterFoes = CommunityRecyclerAdapter(view.context, ArrayList<Community?>(), this)
        recyclerViewFoes.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewFoes.adapter = adapterFoes
        recyclerViewFoes.addOnItemTouchListener(onItemTouchListener)
    }

    private fun updateAdapter(response: CommunityResponse) {
        adapterFriends.updateList(ArrayList(response.friends))
        adapterFoes.updateList(ArrayList(response.foes))
    }

    override fun onItemClick(item: Community) {
        Toast.makeText(context, item.name, Toast.LENGTH_SHORT).show();
    }
}