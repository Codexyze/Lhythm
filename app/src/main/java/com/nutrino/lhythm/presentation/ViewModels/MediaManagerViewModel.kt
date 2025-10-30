package com.nutrino.lhythm.presentation.ViewModels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.exoplayer.ExoPlayer
import com.nutrino.lhythm.core.Media.MediaPlayerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MediaManagerViewModel @Inject constructor(private val mediaMananger: MediaPlayerManager, ): ViewModel() {
    private val _currentSongPositionState = MutableStateFlow<Float>(value = 0F)
    val currentSongPositionState =_currentSongPositionState.asStateFlow()
    private val _isplayingState = MutableStateFlow<Boolean>(value = false)
    val isplayingState =_isplayingState.asStateFlow()
    private val _songQueue = MutableStateFlow<List<Uri>>(emptyList())
    val songQueue = _songQueue.asStateFlow()
    private  val _index = MutableStateFlow(0)
    val index = _index.asStateFlow()


//    init {
//      viewModelScope.launch {
//          while (isPlaying()){
//              getCurrentPosition()
//              delay(2000)
//          }
//
//      }
//
//   }
    fun exoplayeInstance(): ExoPlayer?{
       return mediaMananger.exoPlayerExternal
    }

    fun playMusic(uri: Uri){
        mediaMananger.initializePlayer(uri = uri)

    }

    fun pauseMusic(){
        mediaMananger.pause()
        _isplayingState.value = false
    }

    fun playMusic(){
        mediaMananger.play()
        _isplayingState.value = true
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


fun getCurrentPosition() {
    val player = mediaMananger.getPlayer()
    if (player != null) {
        _currentSongPositionState.value = mediaMananger.getCurrentPosition()
    } else {
        _currentSongPositionState.value = 0f // fallback when player not ready
    }
}

    fun playPlayList(listOfSongsUri: List<Uri>) {
        mediaMananger.playListPlay(listOfSongsUri)
    }

    fun playPlayListWithIndex(listOfSongsUri: List<Uri>,index: Int=0 ,context: Context) {
        _index.value=index
        _songQueue.value=listOfSongsUri
        mediaMananger.playPlayListWithIndex(listOfSongsUri,index)
    }

    fun stopAndCleanup() {
        mediaMananger.releasePlayer()
        viewModelScope.cancel() // 🔥 Stop all coroutines so nothing touches ExoPlayer again
    }

    override fun onCleared() {
        stopAndCleanup()
        super.onCleared()
    }


}