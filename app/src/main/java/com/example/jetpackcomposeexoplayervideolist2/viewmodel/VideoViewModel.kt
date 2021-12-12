package com.example.jetpackcomposeexoplayervideolist2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jetpackcomposeexoplayervideolist2.models.VideoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor() :ViewModel(){

    val videos = MutableLiveData<List<VideoItem>>()
    val currentPlayingIndex = MutableLiveData<Int?>()

    init {
        populateListWithSampleData()
    }

    private fun populateListWithSampleData() {
        val sampleVideos = listOf(
            VideoItem(
                1,
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ElephantsDream.jpg"
            ),
            VideoItem(
                2,
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/BigBuckBunny.jpg"
            ),
            VideoItem(
                3,
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerBlazes.jpg"
            ),
            VideoItem(
                4,
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerEscapes.jpg"
            ),
            VideoItem(
                5,
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerFun.jpg"
            ),
            VideoItem(
                6,
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4",
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerJoyrides.jpg"
            ),
            VideoItem(
                7,
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4",
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerMeltdowns.jpg"
            ),
            VideoItem(
                8,
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/Sintel.jpg"
            ),
            VideoItem(
                9,
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4",
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/SubaruOutbackOnStreetAndDirt.jpg"
            ),
            VideoItem(
                10,
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/TearsOfSteel.jpg"
            ),
        )

        videos.postValue(sampleVideos)
    }

    fun onPlayVideoClick(playbackPosition:Long, videosIndex: Int){

        when(currentPlayingIndex.value){

            null->currentPlayingIndex.postValue(videosIndex)

            videosIndex ->{
                currentPlayingIndex.postValue(null)

                videos.value = videos!!.value!!.toMutableList().also { list->

                    list[videosIndex] = list[videosIndex].copy(lastPlayedPosition = playbackPosition)

                }
            }
            else ->{
                videos.value = videos.value!!.toMutableList().also { list->

                    list[currentPlayingIndex.value!!] = list[currentPlayingIndex.value!!].copy(
                        lastPlayedPosition = playbackPosition
                    )
                }
                currentPlayingIndex.postValue(videosIndex)
            }
        }

    }

}