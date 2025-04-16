package com.example.lhythm.domain.Usecases

import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.data.Song.Song
import com.example.lhythm.domain.Repository.GetAllSongsASC
import kotlinx.coroutines.flow.Flow

class GetAllSongsASCUseCase(private val getAllSongsASC: GetAllSongsASC) {
    suspend operator fun invoke(): Flow<ResultState<List<Song>>>{
        return getAllSongsASC.getAllSongsInASCOrder()
    }
}