package com.tydev.millietest.core.domain.usecase

import com.tydev.millietest.core.domain.repository.NewsRepository
import com.tydev.millietest.core.model.data.Article
import javax.inject.Inject

class SetTopHeadlineAsReadUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(article: Article) =
        newsRepository.setTopHeadlineAsRead(article)
}
