package com.example.moonx.api

import com.example.moonx.model.CompletionRequest
import com.example.moonx.model.CompletionResponse
import com.example.moonx.util.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface GptAPI {
    @Headers("Authorization: Bearer ${Constants.BEARER_TOKEN}")
    @POST("v1/completions")
    suspend fun getEditedText(@Body compRequest: CompletionRequest): Response<CompletionResponse>
}