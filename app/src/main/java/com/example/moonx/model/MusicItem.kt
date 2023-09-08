package com.example.moonx.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MusicItem(
    val albumCover: Int,
    val artist: String,
    val songTitle: String,
    val duration: String,
    val musicUrl: String
    ) : Parcelable