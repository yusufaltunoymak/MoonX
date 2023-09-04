package com.example.moonx.room

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Birth(
    val birthDate: String,
    val birthTime: String,
    val birthPlace: String,
)
