package com.example.jetpackcomposeexoplayervideolist2.manualplayback

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter


@OptIn(ExperimentalCoilApi::class)
@Composable
fun VideoThumbnail(url:String){

    Image(painter = rememberImagePainter(data = url, builder ={
        crossfade(true)
        size(512, 512)
    }),
        contentDescription = "Thumbnail",
        modifier = Modifier
            .fillMaxWidth()
            .size(256.dp),
        contentScale = ContentScale.Crop
    )

}