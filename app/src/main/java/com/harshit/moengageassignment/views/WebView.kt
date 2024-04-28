package com.harshit.moengageassignment.views

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

/**
 *  Composable function to display a WebView to load a web page.
**/
@Composable
fun WebView(url: String?, webViewClient: WebViewClient = WebViewClient()) {
    if(url==null){
        return
    }
    val saveUrl by remember { mutableStateOf(url) }
    AndroidView(
        factory = { context ->
            // Configure the WebView
            WebView(context).apply {
                settings.apply {
                    javaScriptEnabled = true
                }
                this.webViewClient = webViewClient
            }
        },
        update = { webView ->
            // Load the URL in the WebView
            webView.loadUrl(saveUrl)
        },
        modifier = Modifier.fillMaxSize()
    )
}