package com.tydev.millietest.core.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tydev.millietest.core.local.model.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsArticleDao {

    @Query("SELECT * FROM ArticleEntity")
    fun getAll(): Flow<List<ArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg articles: ArticleEntity)

    @Query("DELETE FROM ArticleEntity")
    suspend fun deleteNewsArticles()
}