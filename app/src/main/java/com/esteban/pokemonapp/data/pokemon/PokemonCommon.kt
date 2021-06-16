package com.esteban.pokemonapp.data.pokemon

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Ignore
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class PokemonCommon (
    @SerializedName("id")
    var id: Int,

    @SerializedName("name")
    var name: String,

    @SerializedName("captured_at")
    @ColumnInfo(name = "captured_at")
    var capturedAt: String,

    @SerializedName("captured_lat_at")
    @ColumnInfo(name = "captured_lat_at")
    var capturedLatitudeAt: Double,

    @SerializedName("captured_long_at")
    @ColumnInfo(name = "captured_long_at")
    var capturedLongitudeAt: Double,

    @Ignore
    @SerializedName("pokemon_details")
    var pokemonDetails: PokemonEntity?
) : Parcelable