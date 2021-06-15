package com.esteban.pokemonapp.data.captured

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "captured_table")
data class CapturedEntity (
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("captured_at")
    @ColumnInfo(name = "captured_at")
    val capturedAt: String,

    @SerializedName("captured_lat_at")
    @ColumnInfo(name = "captured_lat_at")
    val capturedLatitudeAt: Double,

    @SerializedName("captured_long_at")
    @ColumnInfo(name = "captured_long_at")
    val capturedLongitudeAt: Double
): Parcelable