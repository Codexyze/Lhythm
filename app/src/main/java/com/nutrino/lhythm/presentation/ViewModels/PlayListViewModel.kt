package com.nutrino.lhythm.presentation.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nutrino.lhythm.core.StateHandeling.ResultState
import com.nutrino.lhythm.data.Local.PlayListSongMapper
import com.nutrino.lhythm.data.Local.PlayListTable
import com.nutrino.lhythm.data.Local.SongEntity
import com.nutrino.lhythm.domain.StateHandeling.CreateOrUpdatePlayListState
import com.nutrino.lhythm.domain.StateHandeling.DeletePlayListSongsState
import com.nutrino.lhythm.domain.StateHandeling.DeletePlayListState
import com.nutrino.lhythm.domain.StateHandeling.DeleteSongFromPlayListState
import com.nutrino.lhythm.domain.StateHandeling.GetAllPlayListSongsState
import com.nutrino.lhythm.domain.StateHandeling.GetAllPlayListState
import com.nutrino.lhythm.domain.StateHandeling.GetLyricsFromPlaylistState
import com.nutrino.lhythm.domain.StateHandeling.GetSongByPlayListIDState
import com.nutrino.lhythm.domain.StateHandeling.GetSongsFromPlayListState
import com.nutrino.lhythm.domain.StateHandeling.InsertSongsToPlayListState
import com.nutrino.lhythm.domain.StateHandeling.SearchPlayListSongState
import com.nutrino.lhythm.domain.StateHandeling.UpdateLyricsFromPLState
import com.nutrino.lhythm.domain.StateHandeling.UpsertPlayListSongsState
import com.nutrino.lhythm.domain.Usecases.CreateUpdateNewPlayListUseCase
import com.nutrino.lhythm.domain.Usecases.DeleteClickedPlayListUseCase
import com.nutrino.lhythm.domain.Usecases.DeletePlayListSongsUseCase
import com.nutrino.lhythm.domain.Usecases.DeletePlayListUseCase
import com.nutrino.lhythm.domain.Usecases.GetAllPlayListSongsUseCase
import com.nutrino.lhythm.domain.Usecases.GetAllPlayListUseCase
import com.nutrino.lhythm.domain.Usecases.GetLyricsFromPlayListUseCase
import com.nutrino.lhythm.domain.Usecases.GetSongByPlayListIDUseCase
import com.nutrino.lhythm.domain.Usecases.GetSongsFromPlayListUseCase
import com.nutrino.lhythm.domain.Usecases.InsertSongToPlayListUseCase
import com.nutrino.lhythm.domain.Usecases.SearchFromPlayListUseCase
import com.nutrino.lhythm.domain.Usecases.UpdateLyricsFromPLUseCase
import com.nutrino.lhythm.domain.Usecases.UpsertPlayListSongsUseCase
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
    private val getAllPlayListUseCase: GetAllPlayListUseCase,
    private val getAllPlayListSongsUseCase:GetAllPlayListSongsUseCase,
    private val deletePlayListSongsUseCase:DeletePlayListSongsUseCase,
    private val upsertPlayListSongsUseCase:UpsertPlayListSongsUseCase,
    private val getSongByPlayListIDUseCase: GetSongByPlayListIDUseCase,
    private val updateLyricsFromPlayListUseCase: UpdateLyricsFromPLUseCase
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
    private val _deletePlayListSongsState = MutableStateFlow(DeletePlayListSongsState())
    val deletePlayListSongsState = _deletePlayListSongsState.asStateFlow()
    private val _upsertPlayListSongsState = MutableStateFlow(UpsertPlayListSongsState())
    val upsertPlayListSongsState = _upsertPlayListSongsState.asStateFlow()
    val _getAllPlayListSongsState = MutableStateFlow(GetAllPlayListSongsState())
    val getAllPlayListSongsState = _getAllPlayListSongsState.asStateFlow()
    val _getSongByPlayListIDState = MutableStateFlow(GetSongByPlayListIDState())
    val getSongByPlayListIDState = _getSongByPlayListIDState.asStateFlow()
    val _updateLyricsFromPlayListState = MutableStateFlow(UpdateLyricsFromPLState())
    val updateLyricsFromPlayListState = _updateLyricsFromPlayListState.asStateFlow()

    init {
        getSongsFromPlayList()
        getAllPlayList()
        //getAllPlayListSongs()
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
    fun getAllPlayListSongs(){
        viewModelScope.launch(Dispatchers.IO) {
            getAllPlayListSongsUseCase.invoke().collect { result->
                when(result){
                    is ResultState.Loading->{
                        _getAllPlayListSongsState.value = GetAllPlayListSongsState(isLoading = true)
                    }
                    is ResultState.Success->{
                        _getAllPlayListSongsState.value = GetAllPlayListSongsState(isLoading = false, data = result.data)
                    }
                    is ResultState.Error->{
                        _getAllPlayListSongsState.value = GetAllPlayListSongsState(isLoading = false, error = result.message)
                    }
                }
            }
        }
    }

    fun deletePlayListSongs(playListSongMapper: PlayListSongMapper){
        viewModelScope.launch(Dispatchers.IO) {
            deletePlayListSongsUseCase.invoke(playListSongMapper = playListSongMapper).collect { result->
                when(result){
                    is ResultState.Loading->{
                        _deletePlayListSongsState.value = DeletePlayListSongsState(isLoading = true)
                    }
                    is ResultState.Success->{
                        _deletePlayListSongsState.value = DeletePlayListSongsState(isLoading = false, data = result.data)
                    }
                    is ResultState.Error->{
                        _deletePlayListSongsState.value = DeletePlayListSongsState(isLoading = false, error = result.message)
                    }
                }
            }
        }
    }
    fun upsertPlayListSongs(playListSongMapper: PlayListSongMapper){
        viewModelScope.launch(Dispatchers.IO) {
            upsertPlayListSongsUseCase.invoke(playListSongMapper = playListSongMapper).collect { result->
                when(result){
                    is ResultState.Loading->{
                        _upsertPlayListSongsState.value = UpsertPlayListSongsState(isLoading = true)
                    }
                    is ResultState.Success->{
                        _upsertPlayListSongsState.value = UpsertPlayListSongsState(isLoading = false, data = result.data)
                    }
                    is ResultState.Error->{
                        _upsertPlayListSongsState.value = UpsertPlayListSongsState(isLoading = false, error = result.message)
                    }
                }
            }

        }
    }

    fun getSongByPlayListID(playListId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            getSongByPlayListIDUseCase.invoke(id = playListId).collect {result->
                when(result){
                    is ResultState.Loading->{
                        _getSongByPlayListIDState.value = GetSongByPlayListIDState(isLoading = true)
                    }
                    is ResultState.Success->{
                        _getSongByPlayListIDState.value = GetSongByPlayListIDState(isLoading = false, data = result.data)
                    }
                    is ResultState.Error->{
                        _getSongByPlayListIDState.value = GetSongByPlayListIDState(isLoading = false, error = result.message)
                    }
                }

            }
        }
    }

    fun updateLyricsFromPlayList(id: Int,lyrics: String){
        viewModelScope.launch(Dispatchers.IO) {
            updateLyricsFromPlayListUseCase.invoke(id = id,lyrics=lyrics).collect {result->
                when(result){
                    is ResultState.Loading->{
                        _updateLyricsFromPlayListState.value = UpdateLyricsFromPLState(isLoading = true)
                    }
                    is ResultState.Success->{
                        _updateLyricsFromPlayListState.value = UpdateLyricsFromPLState(isLoading = false, data = result.data)
                    }
                    is ResultState.Error->{
                        _updateLyricsFromPlayListState.value = UpdateLyricsFromPLState(isLoading = false, error = result.message)
                    }
                }


            }

        }

    }

}