package com.tydev.millietest.feature.headline.ui.detail

import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebView(
    modifier: Modifier = Modifier,
    url: String,
) {
    var isLoading by remember { mutableStateOf(true) }
    var backEnabled by remember { mutableStateOf(false) }
    var webView: WebView? = null

    Box(modifier = modifier) {
        AndroidView(
            modifier = modifier,
            factory = { context ->
                WebView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                            isLoading = true
                            backEnabled = view.canGoBack()
                        }
                        override fun onPageFinished(view: WebView, url: String?) {
                            isLoading = false
                        }
                    }
                    settings.javaScriptEnabled = true
                    loadUrl(url)
                    webView = this
                }
            }, update = {
                webView = it
            })
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

    BackHandler(enabled = backEnabled) {
        webView?.goBack()
    }
}