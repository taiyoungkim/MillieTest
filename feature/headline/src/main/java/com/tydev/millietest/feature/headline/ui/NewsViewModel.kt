package com.tydev.millietest.feature.headline.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tydev.millietest.core.domain.usecase.GetTopHeadlinesUseCase
import com.tydev.millietest.core.domain.usecase.SetTopHeadlineAsReadUseCase
import com.tydev.millietest.core.model.data.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val setTopHeadlineAsReadUseCase: SetTopHeadlineAsReadUseCase,
): ViewModel() {

    private val _topHeadlinesState = MutableStateFlow<TopHeadlinesState>(TopHeadlinesState.Loading)
    val topHeadlinesState: StateFlow<TopHeadlinesState> = _topHeadlinesState.asStateFlow()

    init {
        fetchTopHeadlines()
    }

//    val topHeadlinesState: StateFlow<TopHeadlinesState> = getTopHeadlinesUseCase()
//        .map { articles ->
//            TopHeadlinesState.Success(articles)
//        }
//        .catch { exception ->
//            TopHeadlinesState.Error(exception)
//        }
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5000L),
//            initialValue = TopHeadlinesState.Loading
//        )

    private fun fetchTopHeadlines() {
        viewModelScope.launch {
            getTopHeadlinesUseCase().collect { articles ->
                _topHeadlinesState.value = TopHeadlinesState.Success(articles)
            }
        }
    }

    fun markArticleAsRead(article: Article) {
        viewModelScope.launch {
            setTopHeadlineAsReadUseCase(article).collect { updatedArticle ->
                _topHeadlinesState.update { currentState ->
                    when (currentState) {
                        is TopHeadlinesState.Success -> {
                            val updatedArticles = currentState.articles.map {
                                if (it.url == updatedArticle.url && it.publishedAt == updatedArticle.publishedAt) updatedArticle else it
                            }
                            TopHeadlinesState.Success(updatedArticles)
                        }
                        else -> currentState
                    }
                }
            }
        }
    }
}

sealed class TopHeadlinesState {
    object Loading : TopHeadlinesState()
    data class Success(val articles: List<Article>) : TopHeadlinesState()
    data class Error(val exception: Throwable) : TopHeadlinesState()
}
