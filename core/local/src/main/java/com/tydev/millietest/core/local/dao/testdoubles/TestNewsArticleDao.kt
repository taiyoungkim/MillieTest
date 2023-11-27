package com.tydev.millietest.core.local.dao.testdoubles

import com.tydev.millietest.core.local.dao.NewsArticleDao
import com.tydev.millietest.core.local.model.ArticleEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class TestNewsArticleDao : NewsArticleDao {
    private val articles = MutableStateFlow(
        emptyList<ArticleEntity>()
    )

    override fun getAll(): Flow<List<ArticleEntity>> {
        return articles.map { it.sortedByDescending { article -> article.publishedAt } }
    }

    override suspend fun getArticleByUrlAndPublishedAt(url: String, publishedAt: String): ArticleEntity? {
        return articles.value.find { it.url == url && it.publishedAt == publishedAt }
    }

    override suspend fun insertAll(vararg newArticles: ArticleEntity) {
        articles.value = articles.value + newArticles
    }

    override suspend fun insert(article: ArticleEntity) {
        if (!articles.value.any { it.url == article.url && it.publishedAt == article.publishedAt }) {
            articles.value = articles.value + article
        }
    }

    override suspend fun updateReadStatus(url: String, publishedAt: String, isRead: Boolean) {
        articles.update { currentArticles ->
            currentArticles.map { article ->
                if (article.url == url && article.publishedAt == publishedAt) {
                    article.copy(isRead = isRead)
                } else {
                    article
                }
            }
        }
    }

    override suspend fun deleteNewsArticles() {
        articles.value = emptyList()
    }

    override suspend fun delete(article: ArticleEntity) {
        articles.value = articles.value.filterNot { it.url == article.url && it.publishedAt == article.publishedAt }
    }
}
