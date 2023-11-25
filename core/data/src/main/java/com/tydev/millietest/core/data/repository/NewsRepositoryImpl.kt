package com.tydev.millietest.core.data.repository

import com.tydev.millietest.core.domain.repository.NewsRepository
import com.tydev.millietest.core.local.dao.NewsArticleDao
import com.tydev.millietest.core.local.model.asExternalModel
import com.tydev.millietest.core.local.model.asInternalModel
import com.tydev.millietest.core.model.data.Article
import com.tydev.millietest.core.network.NetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NetworkDataSource,
    private val newsArticleDao: NewsArticleDao
): NewsRepository {

    override fun getTopHeadlines(): Flow<List<Article>> = flow {
        try {
            val response = api.getTopHeadlines()
            if (response.status == "ok") {
                val remoteArticles = response.articles.map { it.asInternalModel() }
                newsArticleDao.insertAll(*remoteArticles.toTypedArray())
                emit(remoteArticles.map { it.asExternalModel() }) // Emit the articles after successful fetch and save
            } else {
                emitFallbackData()
            }
        } catch (e: Exception) {
            emitFallbackData()
        }
    }

    private suspend fun FlowCollector<List<Article>>.emitFallbackData() {
        val localData = newsArticleDao.getAll().firstOrNull()
        if (localData.isNullOrEmpty()) throw Exception("No data available")
        emit(localData.map { it.asExternalModel() }) // Emit local data if available
    }
}