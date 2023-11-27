package com.tydev.millietest.core.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tydev.millietest.core.local.model.ArticleEntity
import com.tydev.millietest.core.model.data.ApiResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsArticleDao {

    // Articles are sorted by the earliest date published first.
    // https://newsapi.org/docs/endpoints/top-headlines
    @Query("SELECT * FROM ArticleEntity ORDER BY publishedAt DESC")
    fun getAll(): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM ArticleEntity WHERE url = :url AND publishedAt = :publishedAt")
    suspend fun getArticleByUrlAndPublishedAt(url: String, publishedAt: String): ArticleEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg articles: ArticleEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(article: ArticleEntity)

    @Query("UPDATE ArticleEntity SET isRead = :isRead WHERE url = :url AND publishedAt = :publishedAt")
    suspend fun updateReadStatus(url: String, publishedAt: String, isRead: Boolean)

    @Query("DELETE FROM ArticleEntity")
    suspend fun deleteNewsArticles()

    @Delete
    suspend fun delete(article: ArticleEntity)
}