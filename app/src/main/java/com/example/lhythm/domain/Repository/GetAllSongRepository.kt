package com.example.lhythm.domain.Repository

import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.data.Song.Song
import kotlinx.coroutines.flow.Flow

interface GetAllSongRepository {
    suspend fun getAllSongs(): Flow<ResultState<List<Song>>>
}