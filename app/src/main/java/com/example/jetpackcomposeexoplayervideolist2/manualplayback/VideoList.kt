package com.example.jetpackcomposeexoplayervideolist2.manualplayback

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModel
import com.example.jetpackcomposeexoplayervideolist2.models.VideoItem
import com.example.jetpackcomposeexoplayervideolist2.viewmodel.VideoViewModel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import kotlinx.coroutines.flow.collect


@Composable
fun VideoLists( viewModel: VideoViewModel = hiltViewModel()){

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val exoPlayer = remember(context){SimpleExoPlayer.Builder(context).build()}
    val listState = rememberLazyListState()
    val videos by viewModel.videos.observeAsState(listOf())
    val playingItemIndex by viewModel.currentPlayingIndex.observeAsState()
    val isCurrentItemVisible = remember{ mutableStateOf(false)}

    LaunchedEffect(playingItemIndex){
        if (playingItemIndex == null){
            exoPlayer.pause()
        }else{
            val video = videos[playingItemIndex!!]
            exoPlayer.setMediaItem(MediaItem.fromUri(video.mediaUrl), video.lastPlayedPosition)
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true

        }
    }

    LaunchedEffect(Unit){
        snapshotFlow {
            listState.visibleAreaContainsItem(playingItemIndex, videos)

        }.collect {isItemVisible->
            isCurrentItemVisible.value = isItemVisible
        }
    }

    LaunchedEffect(isCurrentItemVisible.value){
        if (!isCurrentItemVisible.value && playingItemIndex != null){
            viewModel.onPlayVideoClick(exoPlayer.currentPosition, playingItemIndex!!)
        }
    }

    DisposableEffect(exoPlayer){
        val lifecycleObserver = LifecycleEventObserver{ _, event ->
            if (playingItemIndex == null) return@LifecycleEventObserver
            when(event){
                Lifecycle.Event.ON_START -> exoPlayer.play()
                Lifecycle.Event.ON_STOP -> exoPlayer.pause()
            }
        }

        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
            exoPlayer.release()
        }
    }

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Video Lists",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center

                )
            }
        )
    }) {
        
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            state = listState,
            contentPadding = rememberInsetsPaddingValues(
                insets = LocalWindowInsets.current.systemBars,
                applyTop = true,
                applyBottom = true,
                additionalStart = 16.dp,
                additionalEnd = 16.dp,
                additionalBottom = 16.dp
            )
        ){
            itemsIndexed(videos, { _, video -> video.id }){ index, video ->
                
                Spacer(modifier = Modifier.height(16.dp))
                
                VideoCard(
                    videoItem = video,
                    isPlaying = index == playingItemIndex,
                    exoPlayer = exoPlayer,
                    onclick = {
                    viewModel.onPlayVideoClick(exoPlayer.currentPosition, index)
                })
                
            }
        }

    }


}

private fun LazyListState.visibleAreaContainsItem(currentlyPlayedIndex: Int?, videos: List<VideoItem>): Boolean{
    return when{
        currentlyPlayedIndex == null -> false
        videos.isEmpty() -> false
        else->{
            layoutInfo.visibleItemsInfo.map { videos[it.index] }
                .contains(videos[currentlyPlayedIndex])
        }
    }
}