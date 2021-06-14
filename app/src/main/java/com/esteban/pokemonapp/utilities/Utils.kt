package com.esteban.pokemonapp.utilities

import com.esteban.pokemonapp.data.token.TokenEntity

object Utils {

    fun shouldFetchToken(tokenEntity: TokenEntity?): Boolean {
        return if (tokenEntity == null) {
            true
        } else System.currentTimeMillis() > tokenEntity.expiresAt
    }
}