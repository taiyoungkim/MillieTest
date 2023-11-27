package com.tydev.millietest.core.network.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.tydev.millietest.core.model.data.ApiResponse
import com.tydev.millietest.core.model.data.NewsResponse
import com.tydev.millietest.core.network.BuildConfig
import com.tydev.millietest.core.network.NetworkDataSource
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

private interface NetworkApi {
    @GET(value = "/v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "kr",
    ): ApiResponse<NewsResponse>
}

private const val BASE_URL = BuildConfig.BACKEND_URL

@Singleton
class RetrofitNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: Call.Factory,
    responseCallAdapter: CallAdapter.Factory,
): NetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .callFactory(okhttpCallFactory)
        .addConverterFactory(
            networkJson.asConverterFactory("application/json".toMediaType()),
        )
        .addCallAdapterFactory(responseCallAdapter)
        .build()
        .create(NetworkApi::class.java)

    override suspend fun getTopHeadlines(): ApiResponse<NewsResponse> =
        networkApi.getTopHeadlines()

}
