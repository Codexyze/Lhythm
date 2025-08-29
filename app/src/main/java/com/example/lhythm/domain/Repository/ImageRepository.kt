package com.example.lhythm.domain.Repository

import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.data.Image.Images
import com.example.lhythm.data.Local.SongToImage
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    suspend fun getAllImage(): Flow<ResultState<List<Images>>>

    suspend fun mapImgToSong(songToImage: SongToImage):Flow<ResultState<String>>

    suspend fun getAllMappedImgAndSong(): Flow<ResultState<List<SongToImage>>>

    suspend fun deleteMappedImgAndSong(songToImage: SongToImage): Flow<ResultState<String>>

}