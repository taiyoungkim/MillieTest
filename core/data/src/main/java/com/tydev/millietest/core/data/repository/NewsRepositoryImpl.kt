package com.tydev.millietest.core.data.repository

import com.tydev.millietest.core.domain.repository.NewsRepository
import com.tydev.millietest.core.model.data.NewsResponse
import com.tydev.millietest.core.network.NetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NetworkDataSource
): NewsRepository {

    override fun getTopHeadlines(): Flow<NewsResponse> = flow {
        emit(api.getTopHeadlines())
    }

}