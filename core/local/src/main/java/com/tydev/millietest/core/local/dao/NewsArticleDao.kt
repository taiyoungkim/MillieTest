package com.tydev.millietest.core.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tydev.millietest.core.local.model.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsArticleDao {

    @Query("SELECT * FROM ArticleEntity")
    fun getAll(): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM ArticleEntity WHERE url = :url AND publishedAt = :publishedAt")
    suspend fun getArticleByUrlAndPublishedAt(url: String, publishedAt: String): ArticleEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg articles: ArticleEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(article: ArticleEntity)

    @Update
    suspend fun update(article: ArticleEntity)

    @Query("DELETE FROM ArticleEntity")
    suspend fun deleteNewsArticles()
}