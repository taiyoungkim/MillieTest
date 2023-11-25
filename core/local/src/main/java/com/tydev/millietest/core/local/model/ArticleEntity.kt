package com.tydev.millietest.core.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tydev.millietest.core.model.data.Article
import com.tydev.millietest.core.model.data.Source

@Entity(primaryKeys = ["url", "publishedAt"])
data class ArticleEntity(
    val url: String,
    val source: Source?,
    val author: String?,
    val title: String,
    val description: String?,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?
)

fun ArticleEntity.asExternalModel() = Article(
    url = url,
    source = source,
    author = author,
    title = title,
    description = description,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    content = content,
)

fun Article.asInternalModel() = ArticleEntity(
    url = url,
    source = source,
    author = author,
    title = title,
    description = description,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    content = content,
)
