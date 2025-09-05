package com.example.lhythm.data.UserPrefrence

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


private val Context.dataStore by preferencesDataStore("OnBoarding")
class UserPrefrence @Inject constructor(private val context: Context){
    companion object{
        val ON_BOARD_PREFRENCE = booleanPreferencesKey("on_boarding_pref")
    }
    val onBoardingPref: Flow<Boolean> = context.dataStore.data.map {
        it[ON_BOARD_PREFRENCE] ?: true
    }
    suspend fun updateOnBardingPref(){
        context.dataStore.edit {
            it[ON_BOARD_PREFRENCE] = false
        }
    }

}