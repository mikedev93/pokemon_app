package com.esteban.pokemonapp.data.token

import com.esteban.pokemonapp.data.model.TokenResponse

class TokenMapper {

    fun getLocalTokenModel(response: TokenResponse): TokenEntity {
        return TokenEntity(token = response.token, expiresAt = response.expiresAt)
    }

    fun getResponseTokenModel(entity: TokenEntity): TokenResponse {
        return TokenResponse(entity.token, entity.expiresAt)
    }
}