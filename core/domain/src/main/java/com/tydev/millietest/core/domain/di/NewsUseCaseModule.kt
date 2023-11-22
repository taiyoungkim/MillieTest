package com.tydev.millietest.core.domain.di

import com.tydev.millietest.core.domain.repository.NewsRepository
import com.tydev.millietest.core.domain.usecase.GetTopHeadlinesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object NewsUseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideGetTopHeadlinesUseCase(
        newsRepository: NewsRepository
    ): GetTopHeadlinesUseCase {
        return GetTopHeadlinesUseCase(newsRepository)
    }
}