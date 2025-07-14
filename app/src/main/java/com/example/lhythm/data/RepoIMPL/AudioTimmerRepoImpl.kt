package com.example.lhythm.data.RepoIMPL

import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.util.UnstableApi
import androidx.media3.transformer.Composition
import androidx.media3.transformer.EditedMediaItem
import androidx.media3.transformer.ExportException
import androidx.media3.transformer.ExportResult
import androidx.media3.transformer.Transformer
import com.example.lhythm.constants.Constants
import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.domain.Repository.AudioTrimmerRepository
import com.example.lhythm.presentation.Utils.showToastMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

@UnstableApi
class AudioTimmerRepoImpl: AudioTrimmerRepository {
    override suspend fun TrimAudio(
        context: Context,
        uri: Uri,
        startTime: Long,
        endTime: Long,
        filename: String
    ): Flow<ResultState<String>> = flow{
        emit(ResultState.Loading)
        try {
            val clippingConfiguration = MediaItem.ClippingConfiguration.Builder()
                .setStartPositionMs(startTime)
                .setEndPositionMs(endTime)
                .build()
            val mediaItem = MediaItem.Builder().setUri(uri).setClippingConfiguration(clippingConfiguration)
                .build()
            val editedMediaItem = EditedMediaItem.Builder(mediaItem).build()
            val outputFile = File(context.cacheDir, filename)
            val transformer = Transformer.Builder(context).setAudioMimeType(MimeTypes.AUDIO_AAC)
                .addListener(object :Transformer.Listener{
                    override fun onCompleted(composition: Composition, exportResult: ExportResult) {
                        showToastMessage(context = context, text = "Done Processing", type = Constants.TOASTSUCCESS)
                        super.onCompleted(composition, exportResult)
                    }

                    override fun onError(
                        composition: Composition,
                        exportResult: ExportResult,
                        exportException: ExportException
                    ) {
                        showToastMessage(context = context, text = "Done Processing", type = Constants.TOASTERROR)
                        super.onError(composition, exportResult, exportException)
                    }

                }).build()

            transformer.start(editedMediaItem,outputFile.absolutePath)


        }catch (e:Exception){
            emit(ResultState.Error(e.message.toString()))
        }


    }
}