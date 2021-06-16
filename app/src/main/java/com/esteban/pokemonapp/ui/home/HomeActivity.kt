package com.esteban.pokemonapp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.esteban.pokemonapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

    }

    fun showLoading(){
        progress_circular.visibility = View.VISIBLE
    }

    fun hideLoading(){
        progress_circular.visibility = View.GONE
    }
}