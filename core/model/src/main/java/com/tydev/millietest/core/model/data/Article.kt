package com.tydev.millietest.core.model.data

import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val source: Source?,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?,
    val isRead: Boolean = false,
)
