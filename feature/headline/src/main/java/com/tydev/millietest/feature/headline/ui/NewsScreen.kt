package com.tydev.millietest.feature.headline.ui

import android.util.Log
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun NewsRoute(
    viewModel: NewsViewModel = hiltViewModel(),
    onNewsClick: (String) -> Unit,
) {
    val state by viewModel.topHeadlinesState.collectAsStateWithLifecycle()
    NewsScreen(
        state = state,
        onNewsClick = onNewsClick
    )
}

@Composable
fun NewsScreen(
    state: TopHeadlinesState,
    onNewsClick: (String) -> Unit,
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
                                onNewsClick(it.url)
                            }
                        }
                    }
                )
            }
            is TopHeadlinesState.Error -> {
//                ErrorUI(state.exception)
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
