package com.example.moonx.model


import com.google.gson.annotations.SerializedName
import java.sql.Date
import java.sql.Time

data class Day(
    @SerializedName("datetime")
    val datetime: String?,
    @SerializedName("moonphase")
    val moonphase: Double?,
    @SerializedName("sunrise")
    val sunrise: String?,
    @SerializedName("sunset")
    val sunset: String?,
    @SerializedName("moonrise")
    val moonrise: String?,
    @SerializedName("moonset")
    val moonset: String?,
    @SerializedName("temp")
    val temp : Double?
)