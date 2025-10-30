package com.nutrino.lhythm.presentation.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nutrino.lhythm.core.StateHandeling.ResultState
import com.nutrino.lhythm.data.Local.SongToImage
import com.nutrino.lhythm.domain.StateHandeling.DeleteMappedImgAndSongState
import com.nutrino.lhythm.domain.StateHandeling.GetAllImageState
import com.nutrino.lhythm.domain.StateHandeling.GetAllMappedImgAndSongState
import com.nutrino.lhythm.domain.StateHandeling.MapImgToSongState
import com.nutrino.lhythm.domain.Usecases.DeleteMappedImgAndSongUseCase
import com.nutrino.lhythm.domain.Usecases.GetAllImagesUseCase
import com.nutrino.lhythm.domain.Usecases.GetAllMappedImgSongUseCase
import com.nutrino.lhythm.domain.Usecases.MapImgToSongUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor (
    private val getAllImagesUseCase: GetAllImagesUseCase,
    private val mapImgToSongUseCase: MapImgToSongUseCase,
    private val getAllMappedImgAndSong: GetAllMappedImgSongUseCase,
    private val deleteMappedImgAndSong: DeleteMappedImgAndSongUseCase
): ViewModel(){

    private val _getAllImageState = MutableStateFlow(GetAllImageState())
    val getAllImageState =_getAllImageState.asStateFlow()
    private val _mapImgToSongState = MutableStateFlow(MapImgToSongState())
    val mapImgToSong = _mapImgToSongState.asStateFlow()

    private val _getAllMappedImgAndSong = MutableStateFlow(GetAllMappedImgAndSongState())
    val getAllmappedImgAndSong = _getAllMappedImgAndSong.asStateFlow()

    private val _deleteMappedImgAndSong = MutableStateFlow(DeleteMappedImgAndSongState())
    val deletemappedImgAndSong = _deleteMappedImgAndSong.asStateFlow()


    fun getAllImage(){
        viewModelScope.launch {
            getAllImagesUseCase.invoke().flowOn(Dispatchers.IO).collect { result->
                withContext(Dispatchers.Main){
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

    fun mapImgToSong(songToImage: SongToImage){
        viewModelScope.launch {
            mapImgToSongUseCase.invoke(songToImage = songToImage).flowOn(Dispatchers.IO).collect { result->

                withContext(Dispatchers.Main){
                    when(result){
                        is ResultState.Loading -> {
                            _mapImgToSongState.value = MapImgToSongState(isLoading = true)

                        }
                        is ResultState.Error -> {
                            _mapImgToSongState.value = MapImgToSongState(isLoading = false , error = result.message)

                        }
                        is ResultState.Success -> {
                            _mapImgToSongState.value = MapImgToSongState(isLoading = false, data = result.data)

                        }
                    }

                }

            }
        }

    }

    fun getAllMappedImgAndSong(){
        viewModelScope.launch {
            getAllMappedImgAndSong.invoke().flowOn(Dispatchers.IO).collect { result->
                withContext(Dispatchers.Main){
                    when(result){
                        is ResultState.Loading -> {
                            _getAllMappedImgAndSong.value = GetAllMappedImgAndSongState(isLoading = true)

                        }
                        is ResultState.Error -> {
                            _getAllMappedImgAndSong.value = GetAllMappedImgAndSongState(isLoading = false , error = result.message)
                        }
                        is ResultState.Success -> {
                            _getAllMappedImgAndSong.value = GetAllMappedImgAndSongState(isLoading = false , data = result.data)
                        }

                    }
                }
            }
        }

    }

    fun deleteMappedImgAndSong(songToImage: SongToImage){
        viewModelScope.launch {
            deleteMappedImgAndSong.invoke(songToImage = songToImage).flowOn(Dispatchers.IO).collect { result ->
                withContext(Dispatchers.Main) {
                    when(result){
                        is ResultState.Loading -> {
                            _deleteMappedImgAndSong.value = DeleteMappedImgAndSongState(isLoading = true)

                        }
                        is ResultState.Error -> {
                            _deleteMappedImgAndSong.value = DeleteMappedImgAndSongState(
                                isLoading = false, error = result.message
                            )

                        }
                        is ResultState.Success -> {
                            _deleteMappedImgAndSong.value = DeleteMappedImgAndSongState(
                                isLoading = false,
                                data = result.data
                            )
                        }
                    }


                }

            }

        }
    }

}