package com.harshit.moengageassignment

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.firebase.messaging.FirebaseMessaging
import com.harshit.moengageassignment.Const.saveToken
import com.harshit.moengageassignment.models.Article
import com.harshit.moengageassignment.ui.theme.MoEngageAssignmentTheme
import com.harshit.moengageassignment.viewmodels.NewsViewModel
import com.harshit.moengageassignment.views.NewsList
import com.harshit.moengageassignment.views.WebView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    private val viewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getCurrentFirebaseToken()
        setContent {
            MoEngageAssignmentTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContent(viewModel = viewModel)
                }
            }
        }
    }

    @Composable
    private fun MainContent(viewModel: NewsViewModel) {
        // State variables for managing WebView visibility, URL, and news list
        var showWebView by rememberSaveable { mutableStateOf(false) }
        var webViewUrl by rememberSaveable { mutableStateOf<String?>(null) }
        var newsList by remember { mutableStateOf(emptyList<Article>()) }

        // Observe the news list from the ViewModel
        viewModel.newsList.observe(this) { newsList = it }

        // Register onBackPressed callback to handle back navigation
        DisposableEffect(Unit) {
            val onBackPressedCallback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (showWebView) {
                        showWebView = false
                    } else {
                        remove()
                        onBackPressedDispatcher.onBackPressed()
                    }
                }
            }
            onBackPressedDispatcher.addCallback(onBackPressedCallback)
            onDispose {
                onBackPressedCallback.remove()
            }
        }

        // Scaffold to provide basic layout structure
        Scaffold(
            scaffoldState = rememberScaffoldState(),
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
            // Display news list and handle item clicks
            NewsList(
                newsList = newsList,
                onClick = { url ->
                    showWebView = true
                    webViewUrl = if (url?.startsWith("http://") == true) { url.replace("http://", "https://") } else { url }
                },
                paddingValues = paddingValues,
                // Sort news list based on ascending or descending order
                sortNewsList = { ascending ->
                    newsList = sortArticlesByPublishedDate(newsList, ascending)
                }
            )
            // Show WebView for displaying web content
            AnimatedVisibility(visible = showWebView) {
                WebView(url = webViewUrl)
            }
        }
    }

    // Function to parse date string into Date object
    private fun parseDate(dateString: String): Date {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        return format.parse(dateString) ?: Date(0)
    }

    // Function to sort articles by published date
    private fun sortArticlesByPublishedDate(
        articles: List<Article>,
        ascending: Boolean = true
    ): List<Article> {
        return if (ascending) {
            articles.sortedBy { parseDate(it.publishedAt ?: "") }
        } else {
            articles.sortedByDescending { parseDate(it.publishedAt ?: "") }
        }
    }


    private fun getCurrentFirebaseToken() {
        if(!Const.haveToken(applicationContext)) {
//            If we do not have token we will generate it
            FirebaseMessaging.getInstance().token
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.e("Splash", "Fetching FCM registration token failed", task.exception)
                        return@addOnCompleteListener
                    }
                    val token = task.result
                    saveToken(applicationContext,token)
                }
        }
    }

}