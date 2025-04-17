package com.example.lhythm.presentation.ViewModels

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.exoplayer.ExoPlayer
import com.example.lhythm.core.Media.MediaPlayerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaManagerViewModel @Inject constructor(private val mediaMananger: MediaPlayerManager): ViewModel() {
    private val _currentSongPositionState = MutableStateFlow<Float>(value = 0F)
    val currentSongPositionState =_currentSongPositionState.asStateFlow()

   init {
      viewModelScope.launch {
          while (isPlaying()){
              getCurrentPosition()
              delay(2000)
          }

      }
   }

    fun playMusic(uri: Uri){
        mediaMananger.initializePlayer(uri = uri)

    }

    fun pauseMusic(){
        mediaMananger.pause()
    }

    fun playMusic(){
        mediaMananger.play()
    }
    fun releasePlayerResources(){
        mediaMananger.releasePlayer()
    }

    fun getExoplayer(): ExoPlayer? {
        return mediaMananger.getPlayer()
    }
    fun isPlaying(): Boolean{
        return mediaMananger.isPlaying()
    }
    fun stop(){
        return mediaMananger.releasePlayer()
    }
    fun getDuration(): Long? {
        return mediaMananger.getDuration()
    }
    fun getCurrentPosition(){
            _currentSongPositionState.value = mediaMananger.getCurrentPosition()
    }


}