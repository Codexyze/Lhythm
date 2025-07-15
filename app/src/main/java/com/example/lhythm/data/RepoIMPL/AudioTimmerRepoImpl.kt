package com.example.lhythm.data.RepoIMPL

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.util.Log
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
class AudioTimmerRepoImpl : AudioTrimmerRepository {

    override suspend fun TrimAudio(
        context: Context,
        uri: Uri,
        startTime: Long,
        endTime: Long,
        filename: String
    ): Flow<ResultState<String>> = flow {
        emit(ResultState.Loading)
        Log.d("AudioTrim", "Starting TrimAudio function")

        try {
            val clippingConfiguration = MediaItem.ClippingConfiguration.Builder()
                .setStartPositionMs(startTime)
                .setEndPositionMs(endTime)
                .build()
            Log.d("AudioTrim", "Clipping configuration built: $startTime to $endTime")

            val mediaItem = MediaItem.Builder()
                .setUri(uri)
                .setClippingConfiguration(clippingConfiguration)
                .build()
            Log.d("AudioTrim", "Media item created with URI: $uri")

            val editedMediaItem = EditedMediaItem.Builder(mediaItem).build()
            Log.d("AudioTrim", "EditedMediaItem built")

            val outputFile = File(context.cacheDir, filename)
            Log.d("AudioTrim", "Output file path in cache: ${outputFile.absolutePath}")

            val transformer = Transformer.Builder(context)
                .setAudioMimeType(MimeTypes.AUDIO_AAC)
                .addListener(object : Transformer.Listener {
                    override fun onCompleted(composition: Composition, exportResult: ExportResult) {
                        super.onCompleted(composition, exportResult)
                        Log.d("AudioTrim", "Transformation completed. Output at ${outputFile.absolutePath}")

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            val uriSaved = saveToDownloads(context, outputFile, "${filename}_${System.currentTimeMillis()}")
                            Log.d("AudioTrim", "File saved to Downloads: $uriSaved")
                        } else {
                            Log.w("AudioTrim", "Saving to Downloads not supported below Android Q")
                        }

                        showToastMessage(
                            context = context,
                            text = "Audio Saved to Downloads 📁",
                            type = Constants.TOASTSUCCESS
                        )
                    }

                    override fun onError(
                        composition: Composition,
                        exportResult: ExportResult,
                        exportException: ExportException
                    ) {
                        super.onError(composition, exportResult, exportException)
                        Log.e("AudioTrim", "Error during export: ${exportException.message}", exportException)

                        showToastMessage(
                            context = context,
                            text = "Error trimming audio: ${exportException.message}",
                            type = Constants.TOASTERROR
                        )
                    }
                })
                .build()

            Log.d("AudioTrim", "Starting transformation")
            transformer.start(editedMediaItem, outputFile.absolutePath)

        } catch (e: Exception) {
            Log.e("AudioTrim", "Exception caught: ${e.message}", e)
            emit(ResultState.Error(e.message.toString()))
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveToDownloads(context: Context, sourceFile: File, displayName: String): Uri? {
        Log.d("AudioTrim", "Saving file to Downloads...")

        val resolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "$displayName.m4a")
            put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp4")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            put(MediaStore.MediaColumns.IS_PENDING, 1)
        }

        val collection = MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        val itemUri = resolver.insert(collection, contentValues)

        if (itemUri == null) {
            Log.e("AudioTrim", "Failed to create entry in MediaStore Downloads")
            return null
        }

        try {
            resolver.openOutputStream(itemUri)?.use { outputStream ->
                sourceFile.inputStream().use { inputStream ->
                    inputStream.copyTo(outputStream)
                }
                Log.d("AudioTrim", "File copied to Downloads")
            }

            contentValues.clear()
            contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
            resolver.update(itemUri, contentValues, null, null)
            Log.d("AudioTrim", "File marked as not pending")

        } catch (e: Exception) {
            Log.e("AudioTrim", "Error while writing file to Downloads", e)
        }

        return itemUri
    }
}
