package com.example.lhythm.presentation.ViewModels

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.lhythm.core.Media.MediaPlayerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MediaManagerViewModel @Inject constructor(private val mediaMananger: MediaPlayerManager): ViewModel() {

    fun playMusic(uri: Uri){
        mediaMananger.initializePlayer(uri = uri)

    }

    fun pauseMusic(){
        mediaMananger.pause()
    }

    fun playMusic(){
        mediaMananger.play()
    }

}