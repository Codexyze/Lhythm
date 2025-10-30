package com.nutrino.lhythm.presentation.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nutrino.lhythm.data.UserPrefrence.UserPrefrence
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor (private val prefrence: UserPrefrence): ViewModel(){
    private val _onBoardingPrefrence = MutableStateFlow<Boolean?>(null)
    val onBoardingPrefrence = _onBoardingPrefrence.asStateFlow()
    private val _themeSelection = MutableStateFlow<String?>(null)
    val themeSelection = _themeSelection.asStateFlow()
    fun getLatestValueOfPref(){
        viewModelScope.launch {
            prefrence.onBoardingPref.collect {
                _onBoardingPrefrence.value = it
            }
        }

    }
    fun upDateOnBoardingPref(){
        viewModelScope.launch(Dispatchers.IO) {
            prefrence.updateOnBardingPref()
        }
    }
   fun getThemeSelection(){
       viewModelScope.launch(Dispatchers.IO) {
           prefrence.themeSelection.collect {
               _themeSelection.value = it
           }
       }
   }

   fun updateThemeSelection(theme: String){
       viewModelScope.launch(Dispatchers.IO) {
           prefrence.updateThemeSelection(theme)
       }
   }


}