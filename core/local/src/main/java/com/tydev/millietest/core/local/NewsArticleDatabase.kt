package com.tydev.millietest.core.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tydev.millietest.core.local.dao.NewsArticleDao
import com.tydev.millietest.core.local.model.ArticleEntity
import com.tydev.millietest.core.local.util.Converters

@Database(
    entities = [ArticleEntity::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class NewsArticleDatabase : RoomDatabase() {
    abstract fun newsArticleDao(): NewsArticleDao
}