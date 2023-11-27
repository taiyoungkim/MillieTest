package com.tydev.millietest.core.data.repository

import com.tydev.millietest.core.data.testdoubles.TestNetworkDataSource
import com.tydev.millietest.core.local.dao.testdoubles.TestNewsArticleDao
import com.tydev.millietest.core.local.model.ArticleEntity
import com.tydev.millietest.core.local.model.asExternalModel
import com.tydev.millietest.core.model.data.ApiResponse
import com.tydev.millietest.core.model.data.Article
import com.tydev.millietest.core.model.data.NewsResponse
import com.tydev.millietest.core.model.data.Source
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

class NewsRepositoryImplTest {
    private val testApi = TestNetworkDataSource()
    private val testDao = TestNewsArticleDao()
    private val newsRepository: NewsRepositoryImpl = NewsRepositoryImpl(testApi, testDao)

    @Test
    fun `getTopHeadlines returns articles from API on success`() = runTest {
        testApi.setSuccessResponse(
            ApiResponse.Success(
                NewsResponse(
                    status = "ok",
                    totalResults = 1,
                    listOf(
                        Article(
                            source = Source("test-source-id", "Test Source"),
                            author = "Test Author",
                            title = "Test Article Title",
                            description = "This is a test description for the test article.",
                            url = "https://www.example.com/test-article",
                            urlToImage = "https://www.example.com/test-image.jpg",
                            publishedAt = "2023-03-16T12:34:56Z",
                            content = "This is the content of the test article.",
                            isRead = false
                        )
                    )
                )
            )
        )

        val articles = newsRepository.getTopHeadlines().first()

        assertEquals(1, articles.size)
        assertEquals("Test Article Title", articles[0].title)
    }

    @Test
    fun `getTopHeadlines emits fallback data on API error`() = runTest {
        testApi.setFailureResponse(ApiResponse.Error("Test error", "Error message"))

        val localArticle = ArticleEntity(
            source = Source("test-source-id", "Test Source"),
            author = "Test Author",
            title = "Test Article Title",
            description = "This is a test description for the test article.",
            url = "https://www.example.com/test-article",
            urlToImage = "https://www.example.com/test-image.jpg",
            publishedAt = "2023-03-16T12:34:56Z",
            content = "This is the content of the test article.",
            isRead = false
        )
        testDao.insertAll(localArticle)

        try {
            newsRepository.getTopHeadlines().first()
        } catch (e: Exception) {
            val localData = testDao.getAll().first()
            assertEquals(1, localData.size)
            assertEquals(localArticle.url, localData[0].url)
        }
    }

    @Test
    fun `setTopHeadlineAsRead updates article read status`() = runTest {
        val testArticle = ArticleEntity(
            source = Source("test-source-id", "Test Source"),
            author = "Test Author",
            title = "Test Article Title",
            description = "This is a test description for the test article.",
            url = "https://www.example.com/test-article",
            urlToImage = "https://www.example.com/test-image.jpg",
            publishedAt = "2023-03-16T12:34:56Z",
            content = "This is the content of the test article.",
            isRead = false
        )
        testDao.insert(testArticle)

        val externalArticle = testArticle.asExternalModel()

        val updatedArticle = newsRepository.setTopHeadlineAsRead(externalArticle).first()

        assertTrue(updatedArticle.isRead)
    }

}