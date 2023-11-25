package com.tydev.millietest.core.data.di

import com.tydev.millietest.core.data.repository.NewsRepositoryImpl
import com.tydev.millietest.core.domain.repository.NewsRepository
import com.tydev.millietest.core.local.dao.NewsArticleDao
import com.tydev.millietest.core.network.NetworkDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsProvidesModule {

    @Provides
    @Singleton
    fun providePopularListRepository(
        networkDataSource: NetworkDataSource,
        newsArticleDao: NewsArticleDao,
    ): NewsRepository {
        return NewsRepositoryImpl(
            networkDataSource,
            newsArticleDao,
        )
    }
}