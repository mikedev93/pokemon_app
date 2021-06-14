package com.esteban.pokemonapp.data.model

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

class MyTeamResponse (
    @SerializedName("id")
    val pokemonId: Int,

    @SerializedName("name")
    val chosenName: String,

    @SerializedName("captured_at")
    val capturedAt: String,

    @SerializedName("captured_lat_at")
    val capturedLatitudeAt: Double,

    @SerializedName("captured_long_at")
    val capturedLongitudeAt: Double
)