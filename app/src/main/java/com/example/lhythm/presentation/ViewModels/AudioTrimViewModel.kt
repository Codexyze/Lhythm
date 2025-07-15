package com.example.lhythm.presentation.ViewModels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lhythm.core.StateHandeling.ResultState
import com.example.lhythm.domain.StateHandeling.AudioTrimmerState
import com.example.lhythm.domain.Usecases.TrimAudioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudioTrimViewModel @Inject constructor(
    private val audioTrimmerUseCase: TrimAudioUseCase
) : ViewModel() {
    private val _audioTrimmerState = MutableStateFlow(AudioTrimmerState())
    val audioTrimmerState = _audioTrimmerState.asStateFlow()

    fun audioTrimmerState( context: Context,
                           uri: Uri,
                           startTime: Long,
                           endTime: Long,
                           filename: String){

        viewModelScope.launch(Dispatchers.Main) {
            audioTrimmerUseCase.invoke(context = context, uri = uri, startTime = startTime, endTime = endTime,
                filename = filename).collect {result->
                    when(result){
                        is ResultState.Loading -> {
                            _audioTrimmerState.value = AudioTrimmerState(isLoading = true)

                        }
                        is ResultState.Success -> {
                            _audioTrimmerState.value = AudioTrimmerState(isLoading = false, data = result.data)

                        }
                        is ResultState.Error->{
                            _audioTrimmerState.value = AudioTrimmerState(isLoading = false, error = result.message)

                        }

                    }

            }
        }

    }


}