package com.tydev.millietest.core.local.util

import androidx.room.TypeConverter
import com.tydev.millietest.core.model.data.Article
import com.tydev.millietest.core.model.data.Source
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun fromSource(source: Source?): String {
        return source?.let { Json.encodeToString(Source.serializer(), it) } ?: ""
    }

    @TypeConverter
    fun toSource(jsonString: String): Source? {
        return if (jsonString.isNotEmpty()) Json.decodeFromString(Source.serializer(), jsonString) else null
    }

    @TypeConverter
    fun fromNewsArticleList(articles: List<Article>): String {
        return Json.encodeToString(ListSerializer(Article.serializer()), articles)
    }

    @TypeConverter
    fun toNewsArticleList(jsonString: String): List<Article> {
        return Json.decodeFromString(ListSerializer(Article.serializer()), jsonString)
    }
}