package com.nutrino.lhythm.domain.Usecases

import com.nutrino.lhythm.core.StateHandeling.ResultState
import com.nutrino.lhythm.data.Song.Song
import com.nutrino.lhythm.domain.Repository.GetAllSongRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllSongUseCase @Inject constructor(private val getAllSongRepository: GetAllSongRepository) {
    suspend operator fun invoke(): Flow<ResultState<List<Song>>>{
        return getAllSongRepository.getAllSongs()
    }
}