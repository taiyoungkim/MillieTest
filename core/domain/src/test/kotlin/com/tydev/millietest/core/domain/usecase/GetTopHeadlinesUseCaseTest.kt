package com.tydev.millietest.core.domain.usecase

import com.tydev.millietest.core.model.data.Article
import com.tydev.millietest.core.model.data.Source
import com.tydev.millietest.core.testing.repository.TestNewsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail


class GetTopHeadlinesUseCaseTest {

    private val newsRepository = TestNewsRepository()
    private val useCase = GetTopHeadlinesUseCase(newsRepository)

    @Test
    fun `getTopHeadlines returns articles when repository has articles`() = runTest {
        val fakeArticles = listOf(testArticle)
        newsRepository.sendTopHeadline(fakeArticles)

        val result = useCase().first()

        assertEquals(fakeArticles, result)
    }

    @Test
    fun `getTopHeadlines throws exception when repository throws`() = runTest {
        val fakeException = RuntimeException("This is a test exception")
        newsRepository.throwOnGetTopHeadlines(fakeException)

        try {
            useCase().first()
            fail("Exception was expected but not thrown")
        } catch (e: Exception) {
            assertEquals(fakeException, e)
        }
    }

    val testArticle = Article(
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

}