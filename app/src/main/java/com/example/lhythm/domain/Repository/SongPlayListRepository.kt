package com.example.lhythm.domain.Repository

import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.data.Local.PlayListSongMapper
import com.example.lhythm.data.Local.PlayListTable
import com.example.lhythm.data.Local.SongEntity
import kotlinx.coroutines.flow.Flow

interface SongPlayListRepository {

    suspend fun insertSongToPlayList(songEntity: SongEntity): Flow<ResultState<String>>

    suspend fun getSongsFromPlayList(): Flow<ResultState<List<SongEntity>>>

    suspend fun deleteClickedSongFromPlayList(songEntity: SongEntity): Flow<ResultState<String>>

    suspend fun searchFromPlaylist(query: String): Flow<ResultState<List<SongEntity>>>

    suspend fun getLyricsFromPlaylist(id: Int): Flow<ResultState<String>>

    suspend fun createUpdateNewPlayList(playListTable: PlayListTable): Flow<ResultState<String>>

    suspend fun deletePlayList(playListTable: PlayListTable): Flow<ResultState<String>>

    suspend fun getAllPlayList(): Flow<ResultState<List<PlayListTable>>>

    suspend fun upsertPlayListSongs(playListSongMapper: PlayListSongMapper): Flow<ResultState<String>>

    suspend fun deletePlayListSongs(playListSongMapper: PlayListSongMapper): Flow<ResultState<String>>

    suspend fun getAllPlayListSongs(): Flow<ResultState<List<PlayListSongMapper>>>

    suspend fun getSongByPlayListID(id: Int): Flow<ResultState<List<PlayListSongMapper>>>

}