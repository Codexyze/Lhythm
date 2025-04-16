package com.example.lhythm.domain.Usecases

import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.data.Song.Song
import com.example.lhythm.domain.Repository.GetCategoryRepository
import kotlinx.coroutines.flow.Flow

class GetSongCategoryUseCase(private val getCategoryRepository: GetCategoryRepository) {
    suspend operator fun invoke(): Flow<ResultState<List<Song>>>{
        return getCategoryRepository.getAllSongsInASCOrder()
    }
}