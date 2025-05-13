package com.example.lhythm.presentation.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.data.Local.PlayListTable
import com.example.lhythm.data.Local.SongEntity
import com.example.lhythm.domain.StateHandeling.CreateOrUpdatePlayListState
import com.example.lhythm.domain.StateHandeling.DeletePlayListState
import com.example.lhythm.domain.StateHandeling.DeleteSongFromPlayListState
import com.example.lhythm.domain.StateHandeling.GetAllPlayListState
import com.example.lhythm.domain.StateHandeling.GetLyricsFromPlaylistState
import com.example.lhythm.domain.StateHandeling.GetSongsFromPlayListState
import com.example.lhythm.domain.StateHandeling.InsertSongsToPlayListState
import com.example.lhythm.domain.StateHandeling.SearchPlayListSongState
import com.example.lhythm.domain.Usecases.CreateUpdateNewPlayListUseCase
import com.example.lhythm.domain.Usecases.DeleteClickedPlayListUseCase
import com.example.lhythm.domain.Usecases.DeletePlayListUseCase
import com.example.lhythm.domain.Usecases.GetAllPlayListUseCase
import com.example.lhythm.domain.Usecases.GetLyricsFromPlayListUseCase
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
    private val searchSongUseCase:SearchFromPlayListUseCase,
    private val getLyricsFromPlayList: GetLyricsFromPlayListUseCase,
    private val createOrUpdateUseCase: CreateUpdateNewPlayListUseCase,
    private val deletePlayListUseCase: DeletePlayListUseCase,
    private val getAllPlayListUseCase: GetAllPlayListUseCase
)  : ViewModel() {
    private val _getSongFromPlayListState = MutableStateFlow(GetSongsFromPlayListState())
    val getSongFromPlayListState = _getSongFromPlayListState.asStateFlow()
    private val _insertSongToPlaListState = MutableStateFlow(InsertSongsToPlayListState())
    val insertSongToPlaListState = _insertSongToPlaListState.asStateFlow()
    private val _deleteSongFromPlayListState = MutableStateFlow(DeleteSongFromPlayListState())
    val deleteSongFromPlayListState = _deleteSongFromPlayListState.asStateFlow()
    private val _searchSongState = MutableStateFlow(SearchPlayListSongState())
    val searchSongState = _searchSongState.asStateFlow()
    private val _getLyricsFromPlayListState = MutableStateFlow(GetLyricsFromPlaylistState())
    val getLyricsFromPlayListState = _getLyricsFromPlayListState.asStateFlow()
    private val _createOrUpdatePlayListState = MutableStateFlow(CreateOrUpdatePlayListState())
    val createOrUpdatePlayListState = _createOrUpdatePlayListState.asStateFlow()
    private val _deletePlayListState = MutableStateFlow(DeletePlayListState())
    val deletePlayListState = _deletePlayListState.asStateFlow()
    private val _getAllPlayListState = MutableStateFlow(GetAllPlayListState())
    val getAllPlayListState = _getAllPlayListState.asStateFlow()
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

    fun getLyricsFromPlayList(id:Int){
        viewModelScope.launch(Dispatchers.IO) {
            getLyricsFromPlayList.invoke(id=id).collect {result->
               when(result){
                   is ResultState.Loading->{
                      _getLyricsFromPlayListState.value = GetLyricsFromPlaylistState(isLoading = true)
                   }
                   is ResultState.Success->{
                      _getLyricsFromPlayListState.value = GetLyricsFromPlaylistState(isLoading = false, data = result.data)
                   }
                   is ResultState.Error->{
                      _getLyricsFromPlayListState.value = GetLyricsFromPlaylistState(isLoading = false, error = result.message)
                   }
               }

           }

        }
    }

    fun createOrInsertPlayList(playListTable: PlayListTable) {
        viewModelScope.launch(Dispatchers.IO) {
           createOrUpdateUseCase.invoke(playListTable = playListTable).collect {result->
               when(result){
                   is ResultState.Loading->{
                       _createOrUpdatePlayListState.value = CreateOrUpdatePlayListState(isLoading = true)
                   }
                   is ResultState.Success->{
                       _createOrUpdatePlayListState.value = CreateOrUpdatePlayListState(isLoading = false, data = result.data)
                   }
                   is ResultState.Error->{
                       _createOrUpdatePlayListState.value = CreateOrUpdatePlayListState(isLoading = false, error = result.message)
                   }
               }

           }

        }

    }

    fun deletePlayList(playListTable: PlayListTable) {
        viewModelScope.launch(Dispatchers.IO) {
            deletePlayListUseCase.invoke(playListTable = playListTable).collect {result->
                when(result){
                    is ResultState.Loading->{
                        _deletePlayListState.value = DeletePlayListState(isLoading = true)
                    }
                    is ResultState.Success->{
                        _deletePlayListState.value = DeletePlayListState(isLoading = false, data = result.data)
                    }
                    is ResultState.Error->{
                        _deletePlayListState.value = DeletePlayListState(isLoading = false, error = result.message)
                    }
                }

            }

        }

    }
    fun getAllPlayList(){
        viewModelScope.launch(Dispatchers.IO) {
            getAllPlayListUseCase.invoke().collect {result->
                when(result){
                    is ResultState.Loading->{
                        _getAllPlayListState.value = GetAllPlayListState(isLoading = true)
                    }
                    is ResultState.Success->{
                        _getAllPlayListState.value = GetAllPlayListState(isLoading = false, data = result.data)
                    }
                    is ResultState.Error->{
                        _getAllPlayListState.value = GetAllPlayListState(isLoading = false, error = result.message)
                    }
                }

            }

        }

    }

}