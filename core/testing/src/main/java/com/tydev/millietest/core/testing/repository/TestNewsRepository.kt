package com.tydev.millietest.core.testing.repository

import com.tydev.millietest.core.domain.repository.NewsRepository
import com.tydev.millietest.core.model.data.Article
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class TestNewsRepository : NewsRepository {

    private val articleListFlow: MutableSharedFlow<List<Article>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val articleFlow: MutableSharedFlow<Article> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private var getTopHeadlinesException: Throwable? = null
    private var setTopHeadlineAsReadException: Throwable? = null

    override fun getTopHeadlines(): Flow<List<Article>> = flow {
        getTopHeadlinesException?.let { throw it } ?: emitAll(articleListFlow)
    }
    override suspend fun setTopHeadlineAsRead(article: Article): Flow<Article> = flow {
        setTopHeadlineAsReadException?.let { throw it } ?: emitAll(articleFlow)
    }

    fun throwOnGetTopHeadlines(exception: Throwable) {
        getTopHeadlinesException = exception
    }

    fun throwOnSetTopHeadlineAsRead(exception: Throwable) {
        setTopHeadlineAsReadException = exception
    }

    fun sendTopHeadline(articles: List<Article>) {
        articleListFlow.tryEmit(articles)
    }

    fun sendTopHeadlineAsRead(article: Article) {
        articleFlow.tryEmit(article)
    }
}