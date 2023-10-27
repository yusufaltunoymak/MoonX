package com.example.moonx.model.errorResponse


import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName("code")
    val code: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("param")
    val param: String?,
    @SerializedName("type")
    val type: String?
)