package com.nutrino.lhythm.domain.Repository

import com.nutrino.lhythm.core.StateHandeling.ResultState
import com.nutrino.lhythm.data.Song.Song
import kotlinx.coroutines.flow.Flow

interface GetCategoryRepository {
    suspend fun getAllSongsInASCOrder(): Flow<ResultState<List<Song>>>
    suspend fun getAllSongsInDESCOrder(): Flow<ResultState<List<Song>>>
    suspend fun getAllSongsWithArtists(): Flow<ResultState<List<Song>>>
    suspend fun getAllSongsWithYearAsc(): Flow<ResultState<List<Song>>>
    suspend fun getAllSongsComposerAsc(): Flow<ResultState<List<Song>>>
}