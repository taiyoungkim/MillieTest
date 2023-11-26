package com.tydev.millietest

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.tydev.millietest.core.ui.theme.MillieTestTheme
import com.tydev.millietest.feature.headline.ui.NewsRoute
import com.tydev.millietest.feature.headline.ui.detail.HeadlineDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MillieTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NewsRoute(
                        onNewsClick =  {
                            val intent = Intent(this, HeadlineDetailActivity::class.java)
                            intent.putExtra("url", it)

                            startActivity(intent)
                        },
                        onError = {
                            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}
