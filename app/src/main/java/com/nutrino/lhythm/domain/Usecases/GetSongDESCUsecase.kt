package com.nutrino.lhythm.domain.Usecases

import com.nutrino.lhythm.core.StateHandeling.ResultState
import com.nutrino.lhythm.data.Song.Song
import com.nutrino.lhythm.domain.Repository.GetCategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSongDESCUsecase @Inject constructor(private val getCategoryRepository: GetCategoryRepository) {
    suspend operator fun invoke(): Flow<ResultState<List<Song>>>{
        return getCategoryRepository.getAllSongsInDESCOrder()
    }
}