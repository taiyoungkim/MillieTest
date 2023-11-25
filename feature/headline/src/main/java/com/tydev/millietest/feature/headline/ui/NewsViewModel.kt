package com.tydev.millietest.feature.headline.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tydev.millietest.core.domain.usecase.GetTopHeadlinesUseCase
import com.tydev.millietest.core.model.data.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
): ViewModel() {
    val topHeadlinesState: StateFlow<TopHeadlinesState> = getTopHeadlinesUseCase()
        .map { articles ->
            TopHeadlinesState.Success(articles)
        }
        .catch { exception ->
            TopHeadlinesState.Error(exception)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = TopHeadlinesState.Loading
        )
}

sealed class TopHeadlinesState {
    object Loading : TopHeadlinesState()
    data class Success(val articles: List<Article>) : TopHeadlinesState()
    data class Error(val exception: Throwable) : TopHeadlinesState()
}
