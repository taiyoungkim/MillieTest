package com.tydev.millietest.feature.headline.ui.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.tydev.millietest.core.ui.theme.MillieTestTheme

class HeadlineDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val url = intent.getStringExtra("url")

        setContent {
            MillieTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (url != null) {
                        WebView(url = url)
                    } else {
                        Toast.makeText(this, "유효하지 않은 주소입니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}