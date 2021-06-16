package com.esteban.pokemonapp.data.team

import android.os.Parcelable
import android.telecom.Call
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.esteban.pokemonapp.data.community.CommunityPokemon
import com.esteban.pokemonapp.data.pokemon.PokemonEntity
import com.esteban.pokemonapp.data.pokemon.Sprites
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "my_team_table")
data class MyTeamEntity @JvmOverloads constructor(
    @SerializedName("local_id")
    @ColumnInfo(name = "local_id")
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @SerializedName("id")
    @ColumnInfo(name = "id")
    var pokemonId: Int,

    @SerializedName("name")
    @ColumnInfo(name = "name")
    var chosenName: String,

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
        0,
        "",
        "",
        0.0,
        0.0,
        PokemonEntity(0, "", Sprites("", ""), ArrayList(), ArrayList())
    )
}