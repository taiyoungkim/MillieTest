package com.tydev.millietest.feature.headline.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tydev.millietest.core.model.data.Article
import com.tydev.millietest.core.model.data.Source

@Composable
fun NewsRoute(
    viewModel: NewsViewModel = hiltViewModel(),
    onNewsClick: (Article) -> Unit,
    onError: (String) -> Unit,
) {
    val state by viewModel.topHeadlinesState.collectAsStateWithLifecycle()
    NewsScreen(
        state = state,
        onNewsClick = { article ->
            viewModel.markArticleAsRead(article)
            onNewsClick(article)
        },
        onError = onError,
    )
}

@Composable
fun NewsScreen(
    state: TopHeadlinesState,
    onNewsClick: (Article) -> Unit,
    onError: (String) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp.dp
        val columnSize = if (screenWidth > 600.dp) 3 else 1

        when (state) {
            is TopHeadlinesState.Loading -> LoadingUI()
            is TopHeadlinesState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(columnSize),
                    contentPadding = PaddingValues(16.dp),
                    content = {
                        items(state.articles) {
                            NewsItem(
                                news = it
                            ) {
                                onNewsClick(it)
                            }
                        }
                    }
                )
            }
            is TopHeadlinesState.Error -> {
                onError(state.exception.message.toString())
            }
        }
    }
}

@Composable
fun LoadingUI() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewsScreen() {
    val mockState = TopHeadlinesState.Success(
        articles = listOf(
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
            ),
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
            ),
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
            ),
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

    NewsScreen(
        state = mockState,
        onNewsClick = {},
        onError = {}
    )
}
