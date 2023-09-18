package com.example.moonx.model

import java.io.Serializable


data class MusicItem(
    val albumCover: Int,
    val playerCover : Int,
    val artist: String,
    val songTitle: String,
    val duration: String,
    val musicUrl: String,
    val genre : String
    ) : Serializable