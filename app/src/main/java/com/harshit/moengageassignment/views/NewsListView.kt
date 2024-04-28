package com.harshit.moengageassignment.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.harshit.moengageassignment.models.Article
import com.harshit.moengageassignment.models.Source
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Composable function to display a list of news articles
 */
@Composable
fun NewsList(
    newsList: List<Article>, // List of articles
    onClick: (url: String?) -> Unit, // Click listener for article
    paddingValues: PaddingValues, // Padding values for the list
    sortNewsList: (Boolean) -> Unit // Callback for sorting news list
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        // Display the date and sorting heading
        item {
            DisplayDate()
            DisplayHeading { sortNewsList(it) }
        }
        // Display each news article
        items(newsList) { item ->
            ListItem(item, onClick)
        }
    }
}

// Composable function to display a single news article item
@Composable
fun ListItem(item: Article, onClick: (url: String?) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick(item.url) },
        shape = RoundedCornerShape(bottomEnd = 16.dp, topEnd = 16.dp, topStart = 0.dp, bottomStart = 0.dp),
        elevation = CardDefaults.cardElevation(16.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Display article image
                Box(
                    modifier = Modifier
                        .height(110.dp)
                        .fillMaxWidth(0.45f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray)
                ) {
                    if (!item.urlToImage.isNullOrBlank()) {
                        Image(
                            painter = rememberAsyncImagePainter(item.urlToImage),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
                // Display article title and author
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                        .padding(start = 8.dp),
                ) {
                    Text(
                        text = item.title ?: "",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Black,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = if (item.author != null && item.author != "null") {
                            "-" + item.author
                        } else {
                            ""
                        },
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        textAlign = TextAlign.End,
                        maxLines = 1
                    )
                }
            }
            // Display article description and date
            Text(
                text = item.description ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                modifier = Modifier.padding(top = 8.dp)
            )
            if (!item.publishedAt.isNullOrEmpty() && formatDate(item.publishedAt).isNotEmpty()) {
                Text(
                    text = formatDate(item.publishedAt),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }
        }
    }
}

// Utility function to format date
private fun formatDate(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("MMMM dd", Locale.getDefault())
    val date = inputFormat.parse(dateString)
    return date?.let { outputFormat.format(it) } ?: ""
}