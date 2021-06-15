package com.esteban.pokemonapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import timber.log.Timber

@HiltAndroidApp
class PokemonApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        RxJavaPlugins.setErrorHandler(Timber::e)
    }
}