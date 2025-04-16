package com.example.lhythm.domain.Repository

import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.data.Song.Song
import kotlinx.coroutines.flow.Flow

interface GetAllSongsASC {
    suspend fun getAllSongsInASCOrder(): Flow<ResultState<List<Song>>>
}