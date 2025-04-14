package com.example.lhythm.domain.Usecases

import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.data.Song.Song
import com.example.lhythm.domain.Repository.GetAllSongRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllSongUseCase @Inject constructor(private val getAllSongRepository: GetAllSongRepository) {
    suspend operator fun invoke(): Flow<ResultState<List<Song>>>{
        return getAllSongRepository.getAllSongs()
    }
}