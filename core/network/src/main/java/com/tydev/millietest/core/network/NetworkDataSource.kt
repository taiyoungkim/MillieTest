package com.tydev.millietest.core.network

import com.tydev.millietest.core.model.data.NewsResponse

interface NetworkDataSource {

    suspend fun getTopHeadlines(): NewsResponse
}
