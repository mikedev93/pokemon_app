package com.esteban.pokemonapp.data.model

import android.os.Parcelable
import com.esteban.pokemonapp.data.community.Community
import kotlinx.android.parcel.Parcelize

@Parcelize
class CommunityResponse (

    val friends: List<Community>,

    val foes: List<Community>
) : Parcelable