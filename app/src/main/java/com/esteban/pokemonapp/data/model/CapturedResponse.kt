package com.esteban.pokemonapp.data.model

import com.esteban.pokemonapp.data.team.MyTeamEntity
import com.google.gson.annotations.SerializedName

class CapturedResponse (

    @SerializedName("captured")
    val captured: List<MyTeamEntity>
)