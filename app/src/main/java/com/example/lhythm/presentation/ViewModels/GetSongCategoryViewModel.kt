package com.example.lhythm.presentation.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.domain.StateHandeling.GetAllComposerSongASCState
import com.example.lhythm.domain.Usecases.GetByYearASCUseCase
import com.example.lhythm.domain.Usecases.GetSongCategoryUseCase
import com.example.lhythm.domain.Usecases.GetSongDESCUsecase
import com.example.lhythm.domain.Usecases.GetSongsByArtistUseCase
import com.example.lhythm.domain.StateHandeling.GetAllSongsByArtistState
import com.example.lhythm.domain.StateHandeling.GetAllSongsByYearASCState
import com.example.lhythm.domain.StateHandeling.GetAllSongsInASCState
import com.example.lhythm.domain.StateHandeling.GetAllSongsInDSCState
import com.example.lhythm.domain.Usecases.GetAllSongComposerASCUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetSongCategoryViewModel @Inject constructor(private val getSongCategoryUseCase: GetSongCategoryUseCase,
    private val getSongDESCUsecase: GetSongDESCUsecase,
    private  val getSongsByArtistUseCase: GetSongsByArtistUseCase,
    private val getSongsByYearASCUsecase: GetByYearASCUseCase,
    private val getAllSongComposerASCUseCase: GetAllSongComposerASCUseCase): ViewModel() {
    private  val _songsInASCOrderState = MutableStateFlow(GetAllSongsInASCState())
    val songsInASCOrderState =_songsInASCOrderState.asStateFlow()
    private  val _songsInDSCOrderState = MutableStateFlow(GetAllSongsInDSCState())
    val songsInDSCOrderState =_songsInASCOrderState.asStateFlow()
    private val _getAllSongsByArtist = MutableStateFlow(GetAllSongsByArtistState())
    val getAllSongsByArtist = _getAllSongsByArtist.asStateFlow()
    private val _getSongsByYearASCState = MutableStateFlow(GetAllSongsByYearASCState())
    val getSongsByYearASCState = _getSongsByYearASCState.asStateFlow()
    private val _getSongByComposerASCState = MutableStateFlow(GetAllComposerSongASCState())
    val getSongByComposerASCState = _getSongByComposerASCState.asStateFlow()

    init {
        getAllSongsInASC()

    }
    init {
        getAllSongsInDESC()
    }
    init {
        getSongsByArtistSort()
    }
    init {
        getSongsByYearASC()
    }
    init {
        getSongByComposerASC()
    }
    fun getAllSongsInASC(){
        viewModelScope.launch(Dispatchers.IO) {
            getSongCategoryUseCase.invoke().collect {result->
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
    fun getAllSongsInDESC(){
        viewModelScope.launch(Dispatchers.IO) {
            getSongDESCUsecase.invoke().collect {result->
                when(result){
                    is ResultState.Loading->{
                        _songsInDSCOrderState.value = GetAllSongsInDSCState(isLoading = true)
                    }
                    is ResultState.Success ->{
                        _songsInDSCOrderState.value = GetAllSongsInDSCState(isLoading = false, data = result.data)
                    }
                    is ResultState.Error->{
                        _songsInDSCOrderState.value = GetAllSongsInDSCState(isLoading = false, error = result.message)
                    }
                }

            }
        }
    }
    fun getSongsByArtistSort(){
        viewModelScope.launch (Dispatchers.IO){
            getSongsByArtistUseCase.invoke().collect {result->
                when(result){
                    is ResultState.Loading->{
                        _getAllSongsByArtist.value = GetAllSongsByArtistState(isLoading = true)
                    }
                    is ResultState.Success ->{
                        _getAllSongsByArtist.value = GetAllSongsByArtistState(isLoading = false, data = result.data)
                    }
                    is ResultState.Error->{
                        _getAllSongsByArtist.value = GetAllSongsByArtistState(isLoading = false, error = result.message)
                    }
                }

            }

        }

    }

    fun getSongsByYearASC(){
        viewModelScope.launch (Dispatchers.IO){
            getSongsByYearASCUsecase.invoke().collect {result->
                when(result){
                    is ResultState.Loading->{
                        _getSongsByYearASCState.value = GetAllSongsByYearASCState(isLoading = true)
                    }
                    is ResultState.Success ->{
                        _getSongsByYearASCState.value = GetAllSongsByYearASCState(isLoading = false, data = result.data)
                    }
                    is ResultState.Error->{
                        _getSongsByYearASCState.value = GetAllSongsByYearASCState(isLoading = false, error = result.message)
                    }
                }

            }

        }

    }

    fun getSongByComposerASC(){
        viewModelScope.launch (Dispatchers.IO){
            getAllSongComposerASCUseCase.invoke().collect {result->
                when(result){
                    is ResultState.Success->{
                        _getSongByComposerASCState.value = GetAllComposerSongASCState(
                            isLoading = false, data = result.data
                        )
                    }
                    is ResultState.Loading->{
                        _getSongByComposerASCState.value = GetAllComposerSongASCState(
                            isLoading = true
                        )
                    }
                    is ResultState.Error->{
                        _getSongByComposerASCState.value = GetAllComposerSongASCState(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }

            }
        }
    }

}