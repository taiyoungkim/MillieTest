package com.tydev.millietest.core.data.testdoubles

import com.tydev.millietest.core.model.data.ApiResponse
import com.tydev.millietest.core.model.data.Article
import com.tydev.millietest.core.model.data.NewsResponse
import com.tydev.millietest.core.model.data.Source
import com.tydev.millietest.core.network.NetworkDataSource

class TestNetworkDataSource : NetworkDataSource {
    private var successResponse: ApiResponse<NewsResponse> =
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

    private var failureResponse: ApiResponse<NewsResponse> = ApiResponse.Error("Test error", "Error message")

    private var shouldReturnSuccess = true

    fun setSuccessResponse(response: ApiResponse<NewsResponse>) {
        successResponse = response
        shouldReturnSuccess = true
    }

    fun setFailureResponse(response: ApiResponse<NewsResponse>) {
        failureResponse = response
        shouldReturnSuccess = false
    }

    override suspend fun getTopHeadlines(): ApiResponse<NewsResponse> {
        return if (shouldReturnSuccess) successResponse else failureResponse
    }
}