package com.example.lhythm.domain.Usecases

import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.data.Image.Images
import com.example.lhythm.domain.Repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllImagesUseCase @Inject constructor(private val repository: ImageRepository) {
    operator suspend fun invoke(): Flow<ResultState<List<Images>>>{
        return repository.getAllImage()
    }
}