package com.example.lhythm.data.RepoIMPL

import android.content.Context
import android.provider.MediaStore
import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.data.Song.Song
import com.example.lhythm.domain.Repository.GetAllSongsASC
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllSongsInASCImpl @Inject constructor(private val context: Context): GetAllSongsASC {
    override suspend fun getAllSongsInASCOrder(): Flow<ResultState<List<Song>>> = flow{
        val listOfASCsongs= mutableListOf<Song>()
        emit(ResultState.Loading)
        val contentResolver = context.contentResolver
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
        val selection = "${MediaStore.Audio.Media.IS_MUSIC}!=0"
        val sortingOrder ="${MediaStore.Audio.Media.TITLE} ASC"
         try {
            val cursor = contentResolver.query(uri,projection,selection,null,sortingOrder)
             cursor?.use {cursorelement->
                 while(cursorelement.moveToNext()){
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
                     listOfASCsongs.add(song)
                 }
                 emit(ResultState.Success(listOfASCsongs))


             }
         }catch (e: Exception){

             emit(ResultState.Error(e.message.toString()))
         }


    }
}