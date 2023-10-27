package com.example.moonx.util

object Constants {
    const val BASE_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
    const val BASE_URL_GPT = "https://api.openai.com/"
    const val API_KEY = "X67WNK3AG8NMS6EZARXR5M4WR"
    const val BEARER_TOKEN = "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"
    const val METRIC_UNIT = "metric"
    const val INCLUDE_DAYS = "days"
    val dayProperties = listOf(
        "datetime",
        "moonphase",
        "sunrise",
        "sunset",
        "moonrise",
        "moonset",
        "temp"
    )
}