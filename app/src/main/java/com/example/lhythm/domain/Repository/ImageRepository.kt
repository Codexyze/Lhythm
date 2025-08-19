package com.example.lhythm.domain.Repository

import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.data.Image.Images
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    suspend fun getAllImage(): Flow<ResultState<List<Images>>>

}