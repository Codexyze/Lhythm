package com.example.lhythm.domain.Usecases

import android.content.Context
import android.net.Uri
import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.domain.Repository.AudioTrimmerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrimAudioUseCase @Inject constructor(private val repository: AudioTrimmerRepository) {

    suspend operator fun invoke(context: Context,uri: Uri,startTime:Long,endTime:Long,filename: String):
            Flow<ResultState<String>>
    {
        return repository.TrimAudio(context = context, uri = uri,
            startTime = startTime, endTime = endTime, filename = filename
        )

    }
}