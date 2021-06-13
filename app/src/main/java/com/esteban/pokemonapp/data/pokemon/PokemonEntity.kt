package com.esteban.pokemonapp.data.pokemon

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "pokemon_table")
data class PokemonEntity(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("sprites")
    @Embedded
    val sprites: Sprites,

    @SerializedName("moves")
    val moves: List<Move>
): Parcelable