package com.example.domain.common

sealed class ExecutionResult<out T> {
    data class Success<out T>(val data: T) : ExecutionResult<T>()
    data class Error(
        val code: Int? = null,
        val error: String? = null,
        val errorBody: String? = null
    ) : ExecutionResult<Nothing>()
}