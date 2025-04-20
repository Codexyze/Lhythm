package com.example.lhythm.presentation.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.data.Local.FavSongEntity
import com.example.lhythm.domain.StateHandeling.DeleteFavSongState
import com.example.lhythm.domain.StateHandeling.GetAllFavSongState
import com.example.lhythm.domain.StateHandeling.InsertOrUpdateFavSongState
import com.example.lhythm.domain.Usecases.DelFavSongUseCase
import com.example.lhythm.domain.Usecases.GetAllFavSongUseCase
import com.example.lhythm.domain.Usecases.InsertOrUpdateFavSongUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavSongViewModel @Inject constructor(
    private val deleteFavSongUseCase: DelFavSongUseCase,
    private  val insertOrUpdateFavSongUseCase: InsertOrUpdateFavSongUseCase,
    private val getAllFavSongUseCase: GetAllFavSongUseCase
) : ViewModel() {
    private val _inserOrUpdateFavSate = MutableStateFlow(InsertOrUpdateFavSongState())
    val inserOrUpdateFavState = _inserOrUpdateFavSate.asStateFlow()
    private val _deleteFavSongState = MutableStateFlow(DeleteFavSongState())
    val deleteFavSongState = _deleteFavSongState.asStateFlow()
    private val _getAllFavSongState = MutableStateFlow(GetAllFavSongState())
    val getAllFavSongState = _getAllFavSongState.asStateFlow()

    fun insertOrUpdateFavSong(favSongEntity: FavSongEntity){
        viewModelScope.launch(Dispatchers.IO) {
            insertOrUpdateFavSongUseCase.invoke(favSongEntity = favSongEntity).collect {result->
                when(result){
                    is ResultState.Loading->{
                        _inserOrUpdateFavSate.value = InsertOrUpdateFavSongState(isLoading = true)
                    }
                    is ResultState.Success ->{
                        _inserOrUpdateFavSate.value = InsertOrUpdateFavSongState(isLoading = false, data = result.data)
                    }
                    is ResultState.Error->{
                        _inserOrUpdateFavSate.value = InsertOrUpdateFavSongState(isLoading = false, error = result.message)
                    }
                }

            }

        }

    }

    fun deleteFavSong(favSongEntity: FavSongEntity){
        viewModelScope.launch(Dispatchers.IO) {
            deleteFavSongUseCase.invoke(favSongEntity = favSongEntity).collect {result->
                when(result){
                    is ResultState.Loading->{
                        _deleteFavSongState.value = DeleteFavSongState(isLoading = true)
                    }
                    is ResultState.Success ->{
                        _deleteFavSongState.value = DeleteFavSongState(isLoading = false, data = result.data)
                    }
                    is ResultState.Error->{
                        _deleteFavSongState.value = DeleteFavSongState(isLoading = false, error = result.message)
                    }
                }

            }

        }

    }

    fun getAllFavSong(){
        viewModelScope.launch(Dispatchers.IO) {
            getAllFavSongUseCase.invoke().collect {result->
                when(result){
                    is ResultState.Loading->{
                        _getAllFavSongState.value = GetAllFavSongState(isLoading = true)
                    }
                    is ResultState.Success ->{
                        _getAllFavSongState.value = GetAllFavSongState(isLoading = false, data = result.data)
                    }
                    is ResultState.Error->{
                        _getAllFavSongState.value = GetAllFavSongState(isLoading = false, error = result.message)
                    }
                }

            }

        }

    }


}