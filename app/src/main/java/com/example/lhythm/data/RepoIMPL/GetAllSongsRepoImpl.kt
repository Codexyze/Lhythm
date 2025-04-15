package com.example.lhythm.data.RepoIMPL

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.data.Song.Song
import com.example.lhythm.domain.Repository.GetAllSongRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllSongsRepoImpl @Inject constructor(private val context: Context): GetAllSongRepository {
    override suspend fun getAllSongs(): Flow<ResultState<List<Song>>> = flow{
        val songs = mutableListOf<Song>()
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.YEAR,
            MediaStore.Audio.Media.COMPOSER

        )
        val contentResolver = context.contentResolver
        val selection = "${MediaStore.Audio.Media.IS_MUSIC}!=0"
        emit(ResultState.Loading)
        try {
           val cursor = contentResolver.query(uri,projection,selection,null,null)
            cursor?.use {cursorelement->
                while (cursorelement.moveToNext()){
                    val id = cursorelement.getString(0)
                    val path = cursorelement.getString(1)
                    val size = cursorelement.getString(2)
                    val album = cursorelement.getString(3)
                    val title = cursorelement.getString(4)
                    val artist = cursorelement.getString(5)
                    val duration = cursorelement.getString(6)
                    val year = cursorelement.getString(7)
                    val composer = cursorelement.getString(8)
                    val song = Song(
                        id=id,
                        path = path,
                        size = size,
                        album = album,
                        title = title,
                        artist = artist,
                        duration = duration,
                        year = year,
                        composer = composer
                    )
                    songs.add(song)
                }
            }
            emit(ResultState.Success(data = songs))

        }catch (e: Exception){
            emit(ResultState.Error(message = e.message.toString()))

        }

    }
}
//class GetAllSongsRepoImpl @Inject constructor(private val context: Context): GetAllSongRepository {
//    override suspend fun getAllSongs(): Flow<ResultState<List<Song>>> = flow {
//        val songs = mutableListOf<Song>()
//        emit(ResultState.Loading)
//
//        val contentResolver = context.contentResolver
//        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
//        val projection = arrayOf(
//            MediaStore.Audio.Media._ID,
//            MediaStore.Audio.Media.DATA,
//            MediaStore.Audio.Media.SIZE,
//            MediaStore.Audio.Media.ALBUM,
//            MediaStore.Audio.Media.TITLE,
//            MediaStore.Audio.Media.ARTIST,
//            MediaStore.Audio.Media.DURATION,
//            MediaStore.Audio.Media.YEAR,
//            MediaStore.Audio.Media.COMPOSER,
//        )
//        val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"
//
//        try {
//            val cursor = contentResolver.query(uri, projection, selection, null, null)
//
//            cursor?.use {
//                val idCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
//                val pathCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
//                val sizeCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)
//                val albumCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
//                val titleCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
//                val artistCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
//                val durationCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
//                val yearCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media.YEAR)
//                val composerCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media.COMPOSER)
//
//                while (it.moveToNext()) {
//                    val id = it.getLong(idCol)
//                    val path = pathCol?.let { col -> it.getString(col) } ?: ""
//                    val size = sizeCol?.let { col -> it.getString(col) } ?: ""
//                    val album = albumCol?.let { col -> it.getString(col) } ?: "Unknown"
//                    val title = titleCol?.let { col -> it.getString(col) } ?: "Unknown"
//                    val artist = artistCol?.let { col -> it.getString(col) } ?: "Unknown"
//                    val duration = durationCol?.let { col -> it.getString(col) } ?: "0"
//                    val year = yearCol?.let { col -> it.getString(col) } ?: "0"
//                    val composer = composerCol?.let { col -> it.getString(col) } ?: "Unknown"
//
//                    val song = Song(
//                        id = id.toString(),
//                        path = path,
//                        size = size,
//                        album = album,
//                        title = title,
//                        artist = artist,
//                        duration = duration,
//                        year = year,
//                        composer = composer,
//                    )
//
//                    songs.add(song)
//                }
//            }
//
//            emit(ResultState.Success(data = songs))
//        } catch (e: Exception) {
//            emit(ResultState.Error(message = e.message.toString()))
//        }
//    }
//}