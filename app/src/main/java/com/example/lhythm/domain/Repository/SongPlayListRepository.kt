package com.example.lhythm.domain.Repository

import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.data.Local.SongEntity
import kotlinx.coroutines.flow.Flow

interface SongPlayListRepository {

    suspend fun insertSongToPlayList(songEntity: SongEntity)

    suspend fun getSongsFromPlayList(): Flow<ResultState<List<SongEntity>>>
}