package com.harshit.moengageassignment.models;

// Represents the article.
data class Article(
    val source: Source,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)
// Represents the source of an article.
data class Source(
    val id: String?,
    val name: String?
)