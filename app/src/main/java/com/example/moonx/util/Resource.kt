package com.example.moonx.util

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val networkError: Boolean = false

) {

    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(networkError: Boolean, message: String?) :
        Resource<T>(data = null, message = message, networkError = networkError)



}
