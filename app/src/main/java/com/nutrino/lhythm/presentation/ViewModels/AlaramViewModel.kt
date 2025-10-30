package com.nutrino.lhythm.presentation.ViewModels

import androidx.lifecycle.ViewModel
import com.nutrino.lhythm.core.AlaramManager.AlaramManager
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