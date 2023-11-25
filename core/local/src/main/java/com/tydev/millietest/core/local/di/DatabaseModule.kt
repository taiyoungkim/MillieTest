package com.tydev.millietest.core.local.di

import android.content.Context
import androidx.room.Room
import com.tydev.millietest.core.local.NewsArticleDatabase
import com.tydev.millietest.core.local.util.Converters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesNewsDatabase(
        @ApplicationContext context: Context,
    ): NewsArticleDatabase = Room.databaseBuilder(
        context,
        NewsArticleDatabase::class.java,
        "news-article-database",
    ).build()
}