package com.tydev.millietest.core.domain.usecase

import com.tydev.millietest.core.domain.repository.NewsRepository
import javax.inject.Inject

class GetTopHeadlinesUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke() = newsRepository.getTopHeadlines()
}