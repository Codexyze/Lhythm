package com.example.lhythm.domain.Usecases

import com.example.lhythm.data.Local.SongEntity
import com.example.lhythm.domain.Repository.SongPlayListRepository
import javax.inject.Inject

class InsertSongToPlayListUseCase @Inject constructor(private val songPlayListRepository: SongPlayListRepository) {
    suspend operator fun invoke(songEntity: SongEntity){
        songPlayListRepository.insertSongToPlayList(songEntity = songEntity)
    }
}