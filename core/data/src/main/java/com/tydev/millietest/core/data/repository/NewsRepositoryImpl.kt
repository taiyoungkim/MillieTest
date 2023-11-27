package com.tydev.millietest.core.data.repository

import com.tydev.millietest.core.domain.repository.NewsRepository
import com.tydev.millietest.core.local.dao.NewsArticleDao
import com.tydev.millietest.core.local.model.asExternalModel
import com.tydev.millietest.core.local.model.asInternalModel
import com.tydev.millietest.core.model.data.ApiResponse
import com.tydev.millietest.core.model.data.Article
import com.tydev.millietest.core.model.data.NewsResponse
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
        when (val response = api.getTopHeadlines()) {
            is ApiResponse.Success -> handleSuccessfulResponse(response.data)
            is ApiResponse.Error -> emitFallbackData(response)
            else -> emitFallbackData(null)
        }
    }

    override suspend fun setTopHeadlineAsRead(article: Article): Flow<Article> = flow {
        newsArticleDao.updateReadStatus(article.url, article.publishedAt, true)
        val data = newsArticleDao.getArticleByUrlAndPublishedAt(article.url, article.publishedAt)
        if (data != null)
            emit(data.asExternalModel())
    }

    private suspend fun FlowCollector<List<Article>>.handleSuccessfulResponse(response: NewsResponse) {
        val remoteArticles = response.articles.map { it.asInternalModel() }
        val mappingData = remoteArticles.map { article ->
            newsArticleDao.getArticleByUrlAndPublishedAt(article.url, article.publishedAt)?.let { existingArticle ->
                article.copy(isRead = existingArticle.isRead)
            } ?: run {
                newsArticleDao.insert(article)
                article
            }
        }
        emit(mappingData.map { it.asExternalModel() })
    }

    private suspend fun FlowCollector<List<Article>>.emitFallbackData(response: ApiResponse.Error?) {
        val localData = newsArticleDao.getAll().firstOrNull()
        if (localData.isNullOrEmpty()) {
            throw Exception("No data available")
        }
        emit(localData.map { it.asExternalModel() })
        throw Exception(response?.message ?: "Unknown Error")
    }
}
