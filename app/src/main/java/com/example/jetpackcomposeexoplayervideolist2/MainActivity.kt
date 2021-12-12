package com.example.jetpackcomposeexoplayervideolist2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpackcomposeexoplayervideolist2.manualplayback.VideoLists
import com.example.jetpackcomposeexoplayervideolist2.ui.theme.JetPackComposeExoPlayerVideoList2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetPackComposeExoPlayerVideoList2Theme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    VideoLists()
                }
            }
        }
    }
}

