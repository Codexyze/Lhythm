package com.nutrino.lhythm.domain.Repository

import com.nutrino.lhythm.core.StateHandeling.ResultState
import com.nutrino.lhythm.data.Image.Images
import com.nutrino.lhythm.data.Local.SongToImage
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    suspend fun getAllImage(): Flow<ResultState<List<Images>>>

    suspend fun mapImgToSong(songToImage: SongToImage):Flow<ResultState<String>>

    suspend fun getAllMappedImgAndSong(): Flow<ResultState<List<SongToImage>>>

    suspend fun deleteMappedImgAndSong(songToImage: SongToImage): Flow<ResultState<String>>

}