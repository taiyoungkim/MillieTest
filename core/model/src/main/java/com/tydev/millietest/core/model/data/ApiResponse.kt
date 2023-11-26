package com.tydev.millietest.core.model.data

import kotlinx.serialization.Serializable

@Serializable
sealed interface ApiResponse<out T> {
    @Serializable
    data class Success<out T>(val data: T) : ApiResponse<T>

    @Serializable
    data class Error(val code: String, val message: String) : ApiResponse<Nothing>
}
