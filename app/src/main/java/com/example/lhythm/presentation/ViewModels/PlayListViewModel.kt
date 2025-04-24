package com.example.lhythm.presentation.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.data.Local.SongEntity
import com.example.lhythm.domain.StateHandeling.DeleteSongFromPlayListState
import com.example.lhythm.domain.StateHandeling.GetSongsFromPlayListState
import com.example.lhythm.domain.StateHandeling.InsertSongsToPlayListState
import com.example.lhythm.domain.StateHandeling.SearchPlayListSongState
import com.example.lhythm.domain.Usecases.DeleteClickedPlayListUseCase
import com.example.lhythm.domain.Usecases.GetSongsFromPlayListUseCase
import com.example.lhythm.domain.Usecases.InsertSongToPlayListUseCase
import com.example.lhythm.domain.Usecases.SearchFromPlayListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayListViewModel @Inject constructor(
    private val getSongsFromPlayListUseCase: GetSongsFromPlayListUseCase,
    private val insertSongToPlayListUseCase: InsertSongToPlayListUseCase,
    private val deleteSongFromPlayListUseCase: DeleteClickedPlayListUseCase,
    private val searchSongUseCase:SearchFromPlayListUseCase
)  : ViewModel() {
    private val _getSongFromPlayListState = MutableStateFlow(GetSongsFromPlayListState())
    val getSongFromPlayListState = _getSongFromPlayListState.asStateFlow()
    private val _insertSongToPlaListState = MutableStateFlow(InsertSongsToPlayListState())
    val insertSongToPlaListState = _insertSongToPlaListState.asStateFlow()
    private val _deleteSongFromPlayListState = MutableStateFlow(DeleteSongFromPlayListState())
    val deleteSongFromPlayListState = _deleteSongFromPlayListState.asStateFlow()
    private val _searchSongState = MutableStateFlow(SearchPlayListSongState())
    val searchSongState = _searchSongState.asStateFlow()
    init {
        getSongsFromPlayList()
    }


    fun getSongsFromPlayList(){
        viewModelScope.launch(Dispatchers.IO) {
            getSongsFromPlayListUseCase.invoke().collect {result->
                when(result){
                    is ResultState.Loading->{
                        _getSongFromPlayListState.value = GetSongsFromPlayListState(isLoading = true)
                    }
                    is ResultState.Success->{
                        _getSongFromPlayListState.value = GetSongsFromPlayListState(isLoading = false, data = result.data)

                    }
                    is ResultState.Error->{
                        _getSongFromPlayListState.value = GetSongsFromPlayListState(isLoading = false, error = result.message)
                    }
                }

            }

        }

    }

    fun insertSongToPlayList(songEntity: SongEntity){

        viewModelScope.launch(Dispatchers.IO) {
            insertSongToPlayListUseCase.invoke(songEntity = songEntity).collect {result->
                when(result){
                    is ResultState.Loading->{
                        _insertSongToPlaListState.value = InsertSongsToPlayListState(isLoading = true)
                    }
                    is ResultState.Success->{
                        _insertSongToPlaListState.value = InsertSongsToPlayListState(isLoading = false, data = result.data)
                        Log.d("DATABASE","Sucessfully Inserted")
                    }
                    is ResultState.Error->{
                        _insertSongToPlaListState.value = InsertSongsToPlayListState(isLoading = false, error = result.message)
                    }
                }

            }

        }
    }

    fun deleteSongFromPlayList(songEntity: SongEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteSongFromPlayListUseCase.invoke(songEntity = songEntity).collect { result ->
                when(result){
                    is ResultState.Loading->{
                        _deleteSongFromPlayListState.value = DeleteSongFromPlayListState(isLoading = true)
                    }
                    is ResultState.Success->{
                        _deleteSongFromPlayListState.value = DeleteSongFromPlayListState(isLoading = false, data = result.data)
                        Log.d("DATABASE","Sucessfully Deleted")
                    }
                    is ResultState.Error->{
                        _deleteSongFromPlayListState.value = DeleteSongFromPlayListState(isLoading = false, error = result.message)
                    }
                }

            }
        }
    }

    fun searchFromPlayList(query: String){
        viewModelScope.launch(Dispatchers.IO) {
            searchSongUseCase.invoke(query = query).collect {result->
                when(result){
                    is ResultState.Loading->{
                        _searchSongState.value = SearchPlayListSongState(isLoading = true)
                    }
                    is ResultState.Success->{
                        _searchSongState.value = SearchPlayListSongState(isLoading = false, data = result.data)
                    }
                    is ResultState.Error->{
                        _searchSongState.value = SearchPlayListSongState(isLoading = false, error = result.message)
                    }

                }
            }
        }
    }

}