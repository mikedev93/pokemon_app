package com.esteban.pokemonapp.data.team

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "my_team_table")
data class MyTeamEntity (
    @SerializedName("local_id")
    @ColumnInfo(name = "local_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @SerializedName("id")
    @ColumnInfo(name = "id")
    val pokemonId: Int,

    @SerializedName("name")
    @ColumnInfo(name = "name")
    val chosenName: String,

    @SerializedName("captured_at")
    @ColumnInfo(name = "captured_at")
    val capturedAt: String,

    @SerializedName("captured_lat_at")
    @ColumnInfo(name = "captured_lat_at")
    val capturedLatitudeAt: String,

    @SerializedName("captured_long_at")
    @ColumnInfo(name = "captured_long_at")
    val capturedLongitudeAt: String
): Parcelable