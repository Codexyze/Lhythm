package com.nutrino.lhythm.domain.Usecases

import com.nutrino.lhythm.core.StateHandeling.ResultState
import com.nutrino.lhythm.data.Image.Images
import com.nutrino.lhythm.domain.Repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllImagesUseCase @Inject constructor(private val repository: ImageRepository) {
    operator suspend fun invoke(): Flow<ResultState<List<Images>>>{
        return repository.getAllImage()
    }
}