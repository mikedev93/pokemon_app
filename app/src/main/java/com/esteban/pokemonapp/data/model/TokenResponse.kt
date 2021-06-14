package com.esteban.pokemonapp.data.model

import com.google.gson.annotations.SerializedName

class TokenResponse (
    @SerializedName("token")
    var token: String,

    @SerializedName("expiresAt")
    var expiresAt: Long
)