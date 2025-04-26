package com.example.lhythm.core.Media

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.lhythm.constants.Constants
import com.example.lhythm.presentation.Utils.showToastMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaPlayerManager @Inject constructor(private val context: Context) {

    private var exoPlayer: ExoPlayer? = null
     var songList: List<Uri> = emptyList()
//    private val _songList = MutableStateFlow<List<Uri>>(emptyList())
//    var songList: StateFlow<List<Uri>> = _songList.asStateFlow()
//    Log.d("MediaPlayerManager", "songList: $songList")


    fun initializePlayer(uri: Uri) {
        releasePlayer()

        exoPlayer = ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(uri))
            prepare()
            playWhenReady = true
        }
    }

    fun getPlayer(): ExoPlayer? = exoPlayer

    fun pause() {
        exoPlayer?.pause()
    }

    fun play() {
        exoPlayer?.play()
    }

    fun releasePlayer() {
        exoPlayer?.apply {
            this.stop()
            this.release()
        }
        exoPlayer = null
    }

    fun isPlaying(): Boolean {
        // Safely check if exoPlayer is null and return false if it is
        showToastMessage(context = context, text = "Playing",type = Constants.TOASTSUCCESS)
        return exoPlayer?.isPlaying ?: false
    }

    fun getDuration(): Long? {
        return exoPlayer?.duration
    }

    fun getCurrentPosition(): Float {
        return exoPlayer?.currentPosition!!.toFloat()
    }

    fun seekTo(position: Long) {
        exoPlayer?.seekTo(position)
    }

    object MediaPlayerManager {
        private var mediaPlayer: MediaPlayer? = null

        fun releasePlayer() {
            mediaPlayer?.release()
            mediaPlayer = null
        }

        fun pausePlayer() {
            mediaPlayer?.let {
                if (it.isPlaying) {
                    it.pause()
                }

            } ?: Log.e("MediaPlayerManager", "pausePlayer() called but mediaPlayer is null")


        }


        // Optional: add create/start functions too
    }

    fun playListPlay(listOfSongsUri: List<Uri>) {

        releasePlayer()
        val mediaItemList = mutableListOf<MediaItem>()
        for (uri in listOfSongsUri) {
            val mediaItem = MediaItem.fromUri(uri.path!!.toUri())
            mediaItemList.add(mediaItem)
        }
        exoPlayer = ExoPlayer.Builder(context).build().apply {
            setMediaItems(mediaItemList)
            prepare()
            playWhenReady = true
            showToastMessage(context = context, text = "Playing",type = Constants.TOASTSUCCESS)

            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(state: Int) {
                    if (state == Player.STATE_ENDED) {
                        Log.d("MediaPlayerManager", "Playlist ended.")
                    }
                }

                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    val currentIndex = this@apply.currentMediaItemIndex
                    Log.d("MediaPlayerManager", "Now playing index: $currentIndex")
                }
            })
        }

    }
    fun playPlayListWithIndex(listOfSongsUri: List<Uri>,index: Int=0) {
        songList = listOfSongsUri
        releasePlayer()
        val mediaItemList = mutableListOf<MediaItem>()
        for (uri in listOfSongsUri) {
            val mediaItem = MediaItem.fromUri(uri.path!!.toUri())
            mediaItemList.add(mediaItem)
        }
        exoPlayer = ExoPlayer.Builder(context).build().apply {
            setMediaItems(mediaItemList,index, C.INDEX_UNSET.toLong())
            prepare()
            playWhenReady = true
            showToastMessage(context = context, text = "Playing",type = Constants.TOASTSUCCESS)

            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(state: Int) {
                    if (state == Player.STATE_ENDED) {
                        Log.d("MediaPlayerManager", "Playlist ended.")
                    }
                }

                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    val currentIndex = this@apply.currentMediaItemIndex
                    Log.d("MediaPlayerManager", "Now playing index: $currentIndex")
                }
            })
        }

    }
}


 