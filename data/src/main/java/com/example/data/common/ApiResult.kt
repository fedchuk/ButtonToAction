package com.example.data.common

import com.example.domain.common.ExecutionResult
import retrofit2.Call
import java.io.IOException

sealed class ApiResult<T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error<T>(
        val code: Int? = null,
        val errorMessage: String? = null,
        val errorBody: String? = null
    ) : ApiResult<T>()

    fun <R> toResource(transformSuccess: (data: T) -> R): ExecutionResult<R> = when (this) {
        is Success -> ExecutionResult.Success(transformSuccess(data))
        is Error -> ExecutionResult.Error(code, errorMessage, errorBody)
    }
}

@Suppress("UNCHECKED_CAST")
internal fun <T> apiCall(apiCall: () -> Call<T>): ApiResult<T> {
    val response = try {
        apiCall().execute()
    } catch (e: IOException) {
        return ApiResult.Error(errorMessage = e.message)
    }

    return if (response.isSuccessful) {
        val body = response.body()
        if (body == null) {
            ApiResult.Success(null as T)
        } else {
            ApiResult.Success(body)
        }
    } else {
        val error = response.errorBody()
        ApiResult.Error(
            code = response.code(),
            errorMessage = response.message(),
            errorBody = error?.toString()
        )
    }
}