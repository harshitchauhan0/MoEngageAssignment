package com.harshit.moengageassignment.repositories

import android.util.Log
import com.harshit.moengageassignment.models.Article
import com.harshit.moengageassignment.models.Source
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class Repository {
    companion object {
        const val API_URL = "https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json"
    }

//  Fetches news articles from the remote API.
    suspend fun fetchNews(): List<Article> {
        return withContext(Dispatchers.IO) {
            try {
                val jsonResponse = makeHttpRequest()
                parseJsonResponse(jsonResponse)
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }

//  Makes an HTTP GET request to the API URL and retrieves the JSON response.
    private fun makeHttpRequest(): String {
        try {
            val url = URL(API_URL)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            val responseCode = connection.responseCode
            val jsonResponse = StringBuilder()

            if (responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    jsonResponse.append(line)
                }
                reader.close()
                inputStream.close()
            }
            connection.disconnect()
            return jsonResponse.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    }

//  Parses the JSON response string and constructs a list of [Article] objects.
    private fun parseJsonResponse(jsonResponse: String): List<Article> {
        val newsList = mutableListOf<Article>()
        val jsonObject = JSONObject(jsonResponse)
        val jsonArray = jsonObject.getJSONArray("articles")

        for (i in 0 until jsonArray.length()) {
            val articleObject = jsonArray.getJSONObject(i)
            val sourceObject = articleObject.getJSONObject("source")
            val sourceId = sourceObject.optString("id", "")
            val sourceName = sourceObject.optString("name", "")
            val author = articleObject.optString("author", "")
            val title = articleObject.optString("title", "")
            val description = articleObject.optString("description", "")
            val url = articleObject.optString("url", "")
            val imageUrl = articleObject.optString("urlToImage", "")
            val publishedAt = articleObject.optString("publishedAt", "")
            val content = articleObject.optString("content", "")

            val newsArticle = Article(Source(sourceId, sourceName),author,title,description,url,imageUrl,publishedAt,content)
            newsList.add(newsArticle)
        }
        return newsList
    }

}