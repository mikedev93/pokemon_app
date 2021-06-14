package com.esteban.pokemonapp.data.token

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "token_table")
data class TokenEntity (
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @SerializedName("token")
    val token: String,

    @SerializedName("expiresAt")
    val expiresAt: Long
)