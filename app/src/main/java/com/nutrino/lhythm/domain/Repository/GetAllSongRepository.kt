package com.nutrino.lhythm.domain.Repository

import com.nutrino.lhythm.core.StateHandeling.ResultState
import com.nutrino.lhythm.data.Song.Song
import kotlinx.coroutines.flow.Flow

interface GetAllSongRepository {
    suspend fun getAllSongs(): Flow<ResultState<List<Song>>>
}