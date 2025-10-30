package com.nutrino.lhythm.domain.Repository

import android.content.Context
import android.net.Uri
import com.nutrino.lhythm.core.StateHandeling.ResultState
import kotlinx.coroutines.flow.Flow

interface AudioTrimmerRepository {
    suspend fun TrimAudio(context: Context, uri: Uri, startTime: Long, endTime: Long,filename: String): Flow<ResultState<String>>
}