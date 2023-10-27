package com.example.moonx.repo

import com.example.moonx.api.GptAPI
import com.example.moonx.api.WeatherAPI
import com.example.moonx.model.CompletionRequest
import com.example.moonx.util.Constants
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val weatherService: WeatherAPI,
    private val gptService: GptAPI,
) : BaseRepository() {
    suspend fun getData(
        city: String,
        date: String,
        elements: String,
        includeAstronomy: Boolean
    ) = safeApiRequest {
        weatherService.getWeatherData(
            city = city,
            date = date,
            unitGroup = Constants.METRIC_UNIT,
            include = Constants.INCLUDE_DAYS,
            elements = Constants.dayProperties.joinToString(","),
            apiKey = Constants.API_KEY,
            includeAstronomy = true
        )
    }

    suspend fun getGptResponse(
        prompt: String,
        maxTokens: Int
    ) = safeApiRequest {
        gptService.getEditedText(
            CompletionRequest(
            "text-davinci-003",
            prompt = prompt,
            max_tokens = maxTokens,
        ))
    }
}
