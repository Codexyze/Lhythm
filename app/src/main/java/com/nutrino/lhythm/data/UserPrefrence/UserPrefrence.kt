package com.nutrino.lhythm.data.UserPrefrence

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.nutrino.lhythm.constants.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


private val Context.dataStore by preferencesDataStore("OnBoarding")
class UserPrefrence @Inject constructor(private val context: Context){
    companion object{
        val ON_BOARD_PREFRENCE = booleanPreferencesKey("on_boarding_pref")
        val THEME_SELECTION = stringPreferencesKey("theme_selection")
    }
    val onBoardingPref: Flow<Boolean> = context.dataStore.data.map {
        it[ON_BOARD_PREFRENCE] ?: true
    }
    val themeSelection: Flow<String> = context.dataStore.data.map {
        it[THEME_SELECTION] ?: Constants.REDTHEME
    }
    suspend fun updateOnBardingPref(){
        context.dataStore.edit {
            it[ON_BOARD_PREFRENCE] = false
        }
    }
    suspend fun updateThemeSelection(theme: String){
        context.dataStore.edit {
            it[THEME_SELECTION] = theme
        }
    }

}