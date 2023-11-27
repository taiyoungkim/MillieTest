package com.tydev.millietest.core.network.retrofit.ApiResponse

import com.tydev.millietest.core.model.data.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class ApiResponseCallAdapter<R>(
    private val successType: Type
) : CallAdapter<R, Call<ApiResponse<R>>> {
    override fun adapt(call: Call<R>): Call<ApiResponse<R>> = ApiResponseCall(call, successType)

    override fun responseType(): Type = successType
}