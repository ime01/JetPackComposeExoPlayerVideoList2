package com.example.jetpackcomposeexoplayervideolist2.manualplayback

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.example.jetpackcomposeexoplayervideolist2.R
import com.example.jetpackcomposeexoplayervideolist2.models.Onclick
import com.example.jetpackcomposeexoplayervideolist2.models.VideoItem
import com.example.jetpackcomposeexoplayervideolist2.ui.theme.Shapes
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer

@OptIn(ExperimentalCoilApi::class)
@Composable
fun VideoCard (
    modifier: Modifier = Modifier,
    videoItem: VideoItem,
    isPlaying: Boolean,
    exoPlayer: SimpleExoPlayer,
    onclick: Onclick
){
    val isPlayerUiVisible = remember{ mutableStateOf(false)}
    val isPlayButtonVisible = if (isPlayerUiVisible.value) true else !isPlaying

    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color.Black, Shapes.medium)
        .clip(Shapes.medium),
        contentAlignment = Alignment.Center
    ) {

        if (isPlaying){

            VideoPlayer(exoPlayer){uiVisible ->

                if (isPlayerUiVisible.value){
                    isPlayerUiVisible.value = uiVisible
                }else{
                    isPlayerUiVisible.value = true
                }
            }
        }else{
            VideoThumbnail(url = videoItem.thumbnail)
        }
        if (isPlayButtonVisible){
            Icon(painter = painterResource(id = if (isPlaying) R.drawable.exo_ic_pause_circle_filled else R.drawable.exo_ic_play_circle_filled),
                contentDescription = "Play or Pause",
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(72.dp)
                    .clip(RoundedCornerShape(percent = 50))
                    .clickable { onclick() }
            )
        }



    }

}