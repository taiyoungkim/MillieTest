package com.tydev.millietest.core.domain.usecase

import com.tydev.millietest.core.model.data.Article
import com.tydev.millietest.core.model.data.Source
import com.tydev.millietest.core.testing.repository.TestNewsRepository
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SetTopHeadlineAsReadUseCaseTest {

    private val newsRepository = TestNewsRepository()
    private val useCase = SetTopHeadlineAsReadUseCase(newsRepository)

    @Test
    fun `invoke updates isRead to true for the article`() = runTest {
        val updatedArticle = useCase(testArticle).first()

        assertTrue(updatedArticle.isRead)
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