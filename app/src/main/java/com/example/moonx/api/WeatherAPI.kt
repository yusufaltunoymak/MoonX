package com.example.moonx.api

import com.example.moonx.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherAPI {
    @GET("{city}/{date}")
    suspend fun getWeatherData(
       @Path("city") city: String,
       @Path("date") date: String,
       @Query("unitGroup") unitGroup: String,
       @Query("include") include: String,
       @Query("elements") elements: String,
       @Query("key") apiKey: String,
       @Query("includeAstronomy") includeAstronomy: Boolean
    ): Response<WeatherResponse>
}
