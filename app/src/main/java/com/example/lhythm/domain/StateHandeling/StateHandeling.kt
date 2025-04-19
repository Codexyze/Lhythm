package com.example.lhythm.domain.StateHandeling

import com.example.lhythm.data.Local.SongEntity
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

data class GetAllSongsByArtistState(
    val isLoading: Boolean = false,
    val data: List<Song> = emptyList(),
    val error: String ? = null
)
data class GetAllSongsByYearASCState(
    val isLoading: Boolean = false,
    val data: List<Song> = emptyList(),
    val error: String ? = null
)
data class GetSongsFromPlayListState(
    val isLoading: Boolean = false,
    val data : List<SongEntity> = emptyList(),
    val error: String ? = null

)


data class InsertSongsToPlayListState(
    val isLoading: Boolean = false,
    val data: String= "",
    val error: String ? = null
)

data class DeleteSongFromPlayListState(
    val isLoading: Boolean = false,
    val data: String= "",
    val error: String ? = null
)