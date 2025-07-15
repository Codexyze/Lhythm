package com.example.lhythm.presentation.Screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lhythm.constants.Constants
import com.example.lhythm.presentation.Navigation.SOUNDFXSCREEN
import com.example.lhythm.presentation.Utils.LoadingScreen
import com.example.lhythm.presentation.Utils.showToastMessage
import com.example.lhythm.presentation.ViewModels.AudioTrimViewModel
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AudioTrimmerScreen(
    navController: NavController,
    audioTrimViewModel: AudioTrimViewModel = hiltViewModel(),
    uri: String = ""
) {
    val context = LocalContext.current

    // 🔁 Maintain raw text values safely
    val startTimeText = rememberSaveable { mutableStateOf("") }
    val endTimeText = rememberSaveable { mutableStateOf("") }

    // ✅ Converted safe long values
    val startTime = startTimeText.value.toLongOrNull() ?: 0L
    val endTime = endTimeText.value.toLongOrNull() ?: 0L

    val audioTrimState = audioTrimViewModel.audioTrimmerState.collectAsState()

    when {
        audioTrimState.value.isLoading -> {
            LoadingScreen()
        }
        audioTrimState.value.error != null -> {
            Text("Error ${audioTrimState.value.error}")
        }
        audioTrimState.value.data != null -> {
            showToastMessage(context = context, text = "Audio Trimmed", type = Constants.TOASTSUCCESS)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center
    ) {
        Text(uri)
        Spacer(modifier = Modifier.size(16.dp))

        OutlinedTextField(
            value = startTimeText.value,
            onValueChange = { startTimeText.value = it },
            label = { Text("Start Time (ms)", color = MaterialTheme.colorScheme.primary) },
            modifier = Modifier.fillMaxWidth(0.85f),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary
            ),
            textStyle = TextStyle(color = MaterialTheme.colorScheme.primary),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.size(16.dp))

        OutlinedTextField(
            value = endTimeText.value,
            onValueChange = { endTimeText.value = it },
            label = { Text("End Time (ms)", color = MaterialTheme.colorScheme.primary) },
            modifier = Modifier.fillMaxWidth(0.85f),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary
            ),
            textStyle = TextStyle(color = MaterialTheme.colorScheme.primary),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.size(16.dp))

        Button(
            onClick = {
                if (startTimeText.value.isNotEmpty() && endTimeText.value.isNotEmpty()) {
                    audioTrimViewModel.audioTrimmerState(
                        context = context,
                        uri = uri.toUri(),
                        startTime = startTime,
                        endTime = endTime,
                        filename = "test_trimmed_audio"
                    )
                } else {
                    showToastMessage(context, "Please enter both start and end times.", Constants.TOASTERROR)
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .height(60.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Trim Audio", style = MaterialTheme.typography.titleMedium)
        }
    }
}
