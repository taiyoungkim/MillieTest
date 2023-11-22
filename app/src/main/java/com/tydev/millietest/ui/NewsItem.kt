package com.tydev.millietest.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tydev.millietest.core.model.data.Article

@Composable
fun NewsItem(
    news: Article,
    onNewsItemClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(fraction = 3f)
            .height(150.dp)
            .clickable { onNewsItemClick() },
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(8.dp),
            contentScale = ContentScale.Crop,
            model = ImageRequest.Builder(LocalContext.current)
                .data(data = news.urlToImage)
                .build(),
            contentDescription = null
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(2f),
        ) {
            Text(
                modifier = Modifier.padding(top = 8.dp, end = 4.dp),
                text = news.title,
                maxLines = 2,
                lineHeight = 18.sp,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
            )

            Text(
                modifier = Modifier.padding(top = 6.dp, end = 4.dp),
                text = news.description ?: "",
                maxLines = 4,
                color = Black.copy(alpha = 0.7f),
                overflow = TextOverflow.Ellipsis,
                lineHeight = 18.sp,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}


@Preview
@Composable
fun NewsItemPreview() {
    val dummyNewsItem = Article(
        author = "Some Author",
        content = "This is random content This is random content This is random content This is random content This is random content This is random content This is random content This is random content This is random content ",
        description = "This is description This is description This is description This is description This is description",
        publishedAt = "SDJKSDHSJDHSJD",
        source = null,
        title = "NEWS TITLE GOES HERE NEWS TITLE GOES",
        url = "dsdsdsds",
        urlToImage = null

    )

    NewsItem(news = dummyNewsItem) {

    }

}