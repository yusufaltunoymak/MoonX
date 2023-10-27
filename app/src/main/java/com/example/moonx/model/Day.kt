package com.example.moonx.model


import com.google.gson.annotations.SerializedName

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
    val moonRise: String?,
    @SerializedName("moonset")
    val moonSet: String?,
    @SerializedName("temp")
    val temp : Double?
)