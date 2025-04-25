package com.example.lhythm.domain.Usecases

import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.data.Song.Song
import com.example.lhythm.domain.Repository.GetCategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllSongComposerASCUseCase @Inject constructor(
    private val getCategoryRepository: GetCategoryRepository
){
    suspend operator fun invoke(): Flow<ResultState<List<Song>>>{
        return getCategoryRepository.getAllSongsComposerAsc()
    }
}