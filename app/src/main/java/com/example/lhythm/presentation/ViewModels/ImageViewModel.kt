package com.example.lhythm.presentation.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.domain.StateHandeling.GetAllImageState
import com.example.lhythm.domain.Usecases.GetAllImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor (private val getAllImagesUseCase: GetAllImagesUseCase):
    ViewModel(){
        private val _getAllImageState = MutableStateFlow(GetAllImageState())
        val getAllImageState =_getAllImageState.asStateFlow()

    fun getAllImage(){
        viewModelScope.launch(Dispatchers.IO) {
            getAllImagesUseCase.invoke().collect {result->
                when(result){
                    is ResultState.Loading -> {
                        _getAllImageState.value = GetAllImageState(isLoading = false)
                    }
                    is ResultState.Success->{
                        _getAllImageState.value = GetAllImageState(
                            isLoading = false ,
                            data = result.data
                        )
                    }
                    is ResultState.Error->{
                        _getAllImageState.value = GetAllImageState(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }

            }

        }
    }

}