package com.example.lhythm.data.RepoIMPL

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.data.Image.Images
import com.example.lhythm.domain.Repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class ImageRepoImp @Inject constructor(private val context: Context) : ImageRepository {
    override suspend fun getAllImage(): Flow<ResultState<List<Images>>> = flow {
        val imageList = mutableListOf<Images>()
        val contentResolver = context.contentResolver

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME
        )

        try {
            emit(ResultState.Loading)

            val cursor = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, // ✅ correct URI
                projection,
                null,
                null,
                null
            )

            cursor?.use { cursorElement ->
                val idColumn = cursorElement.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val nameColumn = cursorElement.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)

                while (cursorElement.moveToNext()) {
                    val id = cursorElement.getLong(idColumn) // ✅ get as Long
                    val name = cursorElement.getString(nameColumn)

                    // ✅ Build content:// Uri for the image
                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    )

                    val image = Images(
                        path = contentUri.toString(), // ✅ save as string
                        name = name
                    )

                    imageList.add(image)
                }
                emit(ResultState.Success(imageList))
            }

            emit(ResultState.Success(imageList))
        } catch (e: Exception) {
            emit(ResultState.Error(e.toString()))
        }
    }
}
