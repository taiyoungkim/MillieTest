package com.tydev.millietest.core.domain.repository

import com.tydev.millietest.core.model.data.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getTopHeadlines(): Flow<List<Article>>
}