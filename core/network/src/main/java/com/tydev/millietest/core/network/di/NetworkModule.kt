package com.tydev.millietest.core.network.di

import com.tydev.millietest.core.network.BuildConfig
import com.tydev.millietest.core.network.NetworkDataSource
import com.tydev.millietest.core.network.retrofit.ApiResponse.ApiResponseCallAdapterFactory
import com.tydev.millietest.core.network.retrofit.NetworkInterceptor
import com.tydev.millietest.core.network.retrofit.RetrofitNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Singleton
    @Provides
    fun provideNetworkInterceptor(): NetworkInterceptor = NetworkInterceptor()

    @Singleton
    @Provides
    fun provideApiResponseCallAdapterFactory() : CallAdapter.Factory = ApiResponseCallAdapterFactory()

    @Singleton
    @Provides
    fun provideOkHttpClient(
        networkInterceptor: NetworkInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(networkInterceptor)
            .addInterceptor(
                HttpLoggingInterceptor()
                    .apply {
                        if (BuildConfig.DEBUG) {
                            setLevel(HttpLoggingInterceptor.Level.BODY)
                        }
                    }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkDataSource(
        networkJson: Json,
        okhttpCallFactory: OkHttpClient,
        responseCallAdapter: CallAdapter.Factory,
    ): NetworkDataSource {
        return RetrofitNetwork(networkJson, okhttpCallFactory, responseCallAdapter)
    }
}
