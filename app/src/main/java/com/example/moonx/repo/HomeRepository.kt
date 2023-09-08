package com.example.moonx.repo

import com.example.moonx.api.GptAPI
import com.example.moonx.api.WeatherAPI
import com.example.moonx.model.CompletionRequest
import com.example.moonx.model.CompletionResponse
import com.example.moonx.model.Day
import com.example.moonx.model.WeatherResponse
import com.example.moonx.util.Constants
import retrofit2.Response
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val weatherService: WeatherAPI,
    private val gptService: GptAPI
) {

    suspend fun getData(city: String, date: String, elements: List<Day>): Response<WeatherResponse> {
        val response = weatherService.getWeatherData(
            city, date, Constants.METRIC_UNIT, Constants.INCLUDE_DAYS, elements, Constants.API_KEY
        )
        return response
    }

    suspend fun getGptResponse(prompt: String, maxTokens: Int): Response<CompletionResponse> {
        val completionRequest = CompletionRequest(
            "text-davinci-003",
            prompt,
            maxTokens,
        )
        return gptService.getEditedText(completionRequest)
    }


}
