package com.example.lhythm.presentation.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.domain.Usecases.GetAllSongsASCUseCase
import com.example.lhythm.presentation.StateHandeling.GetAllSongsInASCState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetAllSongsASCViewModel @Inject constructor(private val getAllSongsASCUseCase: GetAllSongsASCUseCase): ViewModel() {
    private  val _songsInASCOrderState = MutableStateFlow(GetAllSongsInASCState())
    val songsInASCOrderState =_songsInASCOrderState.asStateFlow()

    init {
        getAllSongsInASC()
    }
    fun getAllSongsInASC(){
        viewModelScope.launch(Dispatchers.IO) {
            getAllSongsASCUseCase.invoke().collect {result->
                when(result){
                    is ResultState.Loading->{
                        _songsInASCOrderState.value = GetAllSongsInASCState(isLoading = true)
                    }
                    is ResultState.Success ->{
                        _songsInASCOrderState.value = GetAllSongsInASCState(isLoading = false, data = result.data)
                    }
                    is ResultState.Error->{
                        _songsInASCOrderState.value = GetAllSongsInASCState(isLoading = false, error = result.message)
                    }
                }

            }
        }
    }

}