package com.example.jetpackcomposeexoplayervideolist2.manualplayback

import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView

@Composable
fun VideoPlayer(exoPlayer: SimpleExoPlayer, onControllerVisibilityChanged: (uiVisible: Boolean) ->  Unit){

    val context = LocalContext.current
    
        AndroidView({

            PlayerView(context).apply {

                setControllerVisibilityListener {

                    onControllerVisibilityChanged(it == View.VISIBLE)
                }
                player = exoPlayer
            }
        },
        modifier = Modifier.height(256.dp)
            .background(Color.Black)
        )

}