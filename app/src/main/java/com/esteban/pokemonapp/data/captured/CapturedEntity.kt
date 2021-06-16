package com.esteban.pokemonapp.data.captured

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.esteban.pokemonapp.data.pokemon.PokemonEntity
import com.esteban.pokemonapp.data.pokemon.Sprites
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "captured_table")
data class CapturedEntity(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
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
) : Parcelable {
    constructor() : this(
        0,
        "",
        "",
        0.0,
        0.0,
        PokemonEntity(0, "", Sprites("", ""), ArrayList(), ArrayList())
    )
}