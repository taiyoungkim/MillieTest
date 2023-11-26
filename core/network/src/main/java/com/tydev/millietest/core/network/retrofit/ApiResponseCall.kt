package com.tydev.millietest.core.network.retrofit

import com.tydev.millietest.core.model.data.ApiResponse
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.Type

class ApiResponseCall<R>(
    private val delegate: Call<R>,
    private val successType: Type,
) : Call<ApiResponse<R>> {

    override fun enqueue(callback: Callback<ApiResponse<R>>) {
        delegate.enqueue(object : Callback<R> {
            override fun onResponse(call: Call<R>, response: Response<R>) {
                callback.onResponse(this@ApiResponseCall, Response.success(response.toApiResponse()))
            }

            private fun Response<R>.toApiResponse(): ApiResponse<R> {
                if (!isSuccessful) {
                    val errorBody = errorBody()?.string()

                    errorBody?.let {
                        try {
                            val jsonObject = Json.parseToJsonElement(it).jsonObject
                            val code = jsonObject["code"]?.jsonPrimitive?.contentOrNull ?: "unknown_error"
                            val message = jsonObject["message"]?.jsonPrimitive?.contentOrNull ?: "Unknown error occurred"

                            return ApiResponse.Error(code, message)
                        } catch (e: Exception) {
                            return ApiResponse.Error(
                                code = code().toString(),
                                message = message()
                            )
                        }
                    }
                }

                body()?.let { body -> return ApiResponse.Success(body) }

                return if (successType == Unit::class.java) {
                    @Suppress("UNCHECKED_CAST")
                    ApiResponse.Success(Unit as R)
                } else {
                    ApiResponse.Error(
                        code = code().toString(),
                        message = message()
                    )
                }
            }

            override fun onFailure(call: Call<R>, t: Throwable) {
                val error = if (t is IOException) {
                    ApiResponse.Error(
                        code = t.cause.toString(),
                        message = t.message.toString(),
                    )
                } else {
                    ApiResponse.Error(
                        code = t.cause.toString(),
                        message = t.message.toString(),
                    )
                }
                callback.onResponse(this@ApiResponseCall, Response.success(error))
            }
        })
    }

    override fun clone(): Call<ApiResponse<R>> {
        TODO("Not yet implemented")
    }

    override fun execute(): Response<ApiResponse<R>> {
        TODO("Not yet implemented")
    }

    override fun isExecuted(): Boolean {
        TODO("Not yet implemented")
    }

    override fun cancel() {
        TODO("Not yet implemented")
    }

    override fun isCanceled(): Boolean {
        TODO("Not yet implemented")
    }

    override fun request(): Request {
        TODO("Not yet implemented")
    }

    override fun timeout(): Timeout {
        TODO("Not yet implemented")
    }
}