package com.example.lhythm.presentation.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LyricsFullScreenView(lyrics: String) {
    Column(modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background)) {
        LazyColumn(modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background)) {
            item {
                Text(lyrics, color = MaterialTheme.colorScheme.secondary)
            }
        }
    }

}