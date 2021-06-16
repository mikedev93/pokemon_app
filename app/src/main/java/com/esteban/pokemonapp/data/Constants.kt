package com.esteban.pokemonapp.data

class Constants {

    companion object {

        const val baseURLServer = "https://us-central1-samaritan-android-assignment.cloudfunctions.net/"
        const val baseURLPokeAPI = "https://pokeapi.co/api/v2/"
        const val authorizedEmail = "miguelparis93@gmail.com"

        /**
         * Used to pass information to DetailActivity
         */
        const val POKEMON_BUNDLE = "BUNDLE"
        const val DETAIL_ORIGIN = "ORIGIN"
        const val WILD = "WILD"
        const val CAPTURED = "CAPTURED"
        const val POKEMON = "POKEMON"
        const val CAPTURED_BY_OTHER = "CAPTURED_BY_OTHER"
    }
}