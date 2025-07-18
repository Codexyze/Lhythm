package com.example.lhythm.domain.StateHandeling

import com.example.lhythm.data.Local.FavSongEntity
import com.example.lhythm.data.Local.PlayListSongMapper
import com.example.lhythm.data.Local.PlayListTable
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

data class GetAllFavSongState(
    val isLoading: Boolean = false,
    val data: List<FavSongEntity> = emptyList(),
    val error: String ? = null
)

data class InsertOrUpdateFavSongState(
    val isLoading: Boolean = false,
    val data: String= "",
    val error: String ? = null
)

data class DeleteFavSongState(
    val isLoading: Boolean = false,
    val data: String= "",
    val error: String ? = null
)

data class SearchPlayListSongState(
    val isLoading: Boolean = false,
    val data: List<SongEntity> = emptyList(),
    val error: String ? = null
)

data class GetAllComposerSongASCState(
    val  isLoading: Boolean = false,
    val data: List<Song> = emptyList(),
    val error: String ? = null

)
data class GetLyricsFromPlaylistState(
    val isLoading: Boolean = false,
    val data: String= "",
    val error: String ? = null
)

data class CreateOrUpdatePlayListState(
    val isLoading: Boolean = false,
    val data: String= "",
    val error: String ? = null
)

data class DeletePlayListState(
    val isLoading: Boolean = false,
    val data: String= "",
    val error: String ? = null
)

data class GetAllPlayListState(
    val isLoading: Boolean = false,
    val data: List<PlayListTable> = emptyList(),
    val error: String ? = null
)

data class DeletePlayListSongsState(
    val isLoading: Boolean = false,
    val data: String= "",
    val error: String ? = null
)

data class UpsertPlayListSongsState(
    val isLoading: Boolean = false,
    val data: String= "",
    val error: String ? = null
)

data class GetAllPlayListSongsState(
    val isLoading: Boolean = false,
    val data: List<PlayListSongMapper> = emptyList(),
    val error: String ? = null
)

data class GetSongByPlayListIDState(
    val isLoading: Boolean = false,
    val data: List<PlayListSongMapper> = emptyList(),
    val error: String ? = null
)

data class UpdateLyricsFromPLState(
    val isLoading: Boolean = false,
    val data: String= "",
    val error: String ? = null
)

data class AudioTrimmerState(
    val isLoading: Boolean = false,
    val data: String= "",
    val error: String ? = null
)