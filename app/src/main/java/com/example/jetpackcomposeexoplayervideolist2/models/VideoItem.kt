package com.example.jetpackcomposeexoplayervideolist2.models

data class VideoItem(
    val id: Int,
    val mediaUrl: String,
    val thumbnail: String,
    val lastPlayedPosition: Long = 0,
)
