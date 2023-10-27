package com.example.moonx.repo
import com.example.moonx.model.errorResponse.ErrorResponse
import com.example.moonx.util.Resource
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.lang.Exception

abstract class BaseRepository {

    suspend fun <T> safeApiRequest(
        apiRequest: suspend () -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiRequest.invoke())
            }
            catch (throwable: Throwable) {

                when(throwable) {

                    is HttpException -> {
                        Resource.Error(false,errorBodyParser(throwable.response()?.errorBody()?.string()))
                    }

                    else -> {
                        Resource.Error(true, throwable.localizedMessage)

                    }
                }
            }
        }
    }

    private fun errorBodyParser(error: String?): String {
        error?.let {
            return try {
                val errorResponse = Gson().fromJson(error, ErrorResponse::class.java)
                val errorMessage = errorResponse.error?.message.toString()
                errorMessage ?: "bilinmeyen bir hata oluştu"
            } catch (e: Exception) {
                "bilinmeyen bir hata oluştu"
            }
        }
        return "bilinmeyen bir hata oluştu"
    }
}
