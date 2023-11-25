package com.tydev.millietest.core.local.di

import com.tydev.millietest.core.local.NewsArticleDatabase
import com.tydev.millietest.core.local.dao.NewsArticleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesNewsArticlesDao(
        database: NewsArticleDatabase,
    ): NewsArticleDao = database.newsArticleDao()
}