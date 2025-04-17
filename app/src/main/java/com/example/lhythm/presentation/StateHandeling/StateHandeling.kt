package com.example.lhythm.presentation.StateHandeling

import com.example.lhythm.data.Song.Song

data class GetAllSongState(
    val isLoading: Boolean = false,
    val data: List<Song> = emptyList(),
    val error: String ? = null
)

data class GetAllSongsInASCState(
    val isLoading: Boolean = false,
    val data: List<Song> = emptyList(),
    val error: String ? = null
)

data class GetAllSongsInDSCState(
    val isLoading: Boolean = false,
    val data: List<Song> = emptyList(),
    val error: String ? = null
)