package com.example.lhythm.presentation.ViewModels

import androidx.lifecycle.ViewModel
import com.example.lhythm.core.AlaramManager.AlaramManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlaramViewModel @Inject constructor(
    private val alaramManager: AlaramManager) : ViewModel() {

    fun scheduleAlaram(time: Long){
        alaramManager.setAlaram(time = time)
    }

    fun cancelAlaram(){
        alaramManager.cancelAlaram()
    }
}