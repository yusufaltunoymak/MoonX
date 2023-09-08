package com.example.moonx.api

import com.example.moonx.model.Day
import com.example.moonx.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherAPI {
    @GET("{city}/{date}")
    suspend fun getWeatherData(
        @Path("city") city: String,
        @Path("date") date: String,
        @Query("unitGroup") unitGroup: Any,
        @Query("include") include: String,
        @Query("elements") elements: List<Day>,
        @Query("key") apiKey: String
    ): Response<WeatherResponse>
}
