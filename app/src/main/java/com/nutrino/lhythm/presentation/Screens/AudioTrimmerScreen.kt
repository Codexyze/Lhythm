package com.nutrino.lhythm.presentation.Screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nutrino.lhythm.constants.Constants
import com.nutrino.lhythm.presentation.Utils.LoadingScreen
import com.nutrino.lhythm.presentation.Utils.showToastMessage
import com.nutrino.lhythm.presentation.ViewModels.AudioTrimViewModel
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AudioTrimmerScreen(
    navController: NavController,
    audioTrimViewModel: AudioTrimViewModel = hiltViewModel(),
    uri: String = "",
    songDuration: Long = 0
) {
    val context = LocalContext.current

    // Check if device supports audio trimming
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
        UnsupportedDeviceScreen()
        LaunchedEffect(Unit) {
            showToastMessage(
                context = context,
                text = "Audio Trimmer requires Android 10 or higher",
                type = Constants.TOASTERROR
            )
        }
        return
    }

    // State management
    val filename = rememberSaveable { mutableStateOf("") }
    val durationInSeconds = remember { songDuration / 1000f }

    // Range slider values (in seconds) - using proper state for ClosedFloatingPointRange
    var sliderPosition by remember {
        mutableStateOf(0f..durationInSeconds.coerceAtLeast(1f))
    }

    val audioTrimState = audioTrimViewModel.audioTrimmerState.collectAsState()

    // Handle success state - show toast and reset form
    LaunchedEffect(audioTrimState.value) {
        if (!audioTrimState.value.isLoading &&
            audioTrimState.value.data.isNotEmpty() &&
            audioTrimState.value.error == null) {
            showToastMessage(
                context,
                "Audio trimmed successfully! ✂️",
                Constants.TOASTSUCCESS
            )
        }
    }

    // Handle loading state
    if (audioTrimState.value.isLoading) {
        LoadingScreen()
        return
    }

    // Handle error state
    if (audioTrimState.value.error != null) {
        ErrorScreen(error = audioTrimState.value.error ?: "Unknown error occurred")
        return
    }

    // Handle success state - reset form after showing main UI briefly
    if (audioTrimState.value.data.isNotEmpty()) {
        LaunchedEffect(Unit) {
            kotlinx.coroutines.delay(500) // Brief delay to show success
            filename.value = ""
            sliderPosition = 0f..durationInSeconds.coerceAtLeast(1f)
        }
    }

    // Main UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.ContentCut,
                    contentDescription = "Trim Audio",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Trim Audio",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Select the portion you want to keep",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.alpha(0.6f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Duration Info Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Total Duration",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.alpha(0.7f)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = formatTime(durationInSeconds.toLong()),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Trim Range Selector Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Select Trim Range",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Time Display Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Start Time
                        TimeDisplay(
                            label = "Start",
                            time = formatTime(sliderPosition.start.toLong()),
                            isStart = true
                        )

                        // Duration Display
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Duration",
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.alpha(0.6f)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = formatTime(
                                    (sliderPosition.endInclusive - sliderPosition.start).toLong()
                                ),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        // End Time
                        TimeDisplay(
                            label = "End",
                            time = formatTime(sliderPosition.endInclusive.toLong()),
                            isStart = false
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Range Slider
                    RangeSlider(
                        value = sliderPosition,
                        onValueChange = { newRange ->
                            // Ensure minimum 1 second duration
                            if (newRange.endInclusive - newRange.start >= 1f) {
                                sliderPosition = newRange
                            }
                        },
                        valueRange = 0f..durationInSeconds.coerceAtLeast(1f),
                        modifier = Modifier.fillMaxWidth(),
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colorScheme.primary,
                            activeTrackColor = MaterialTheme.colorScheme.primary,
                            inactiveTrackColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Helpful hint
                    Text(
                        text = "Drag the handles to select the audio range",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .alpha(0.5f),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Filename Input
            OutlinedTextField(
                value = filename.value,
                onValueChange = { filename.value = it },
                label = {
                    Text(
                        "Output Filename",
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f)
                    )
                },
                placeholder = {
                    Text(
                        "Enter filename...",
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f),
                    focusedTextColor = MaterialTheme.colorScheme.secondary,
                    unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Trim Button
            Button(
                onClick = {
                    val startTime = sliderPosition.start.toLong()
                    val endTime = sliderPosition.endInclusive.toLong()
                    val trimmedFilename = filename.value.trim()

                    // Validation
                    when {
                        uri.isEmpty() -> {
                            showToastMessage(
                                context,
                                "No audio file selected",
                                Constants.TOASTERROR
                            )
                        }
                        trimmedFilename.isEmpty() -> {
                            showToastMessage(
                                context,
                                "Please enter a filename",
                                Constants.TOASTERROR
                            )
                        }
                        startTime >= endTime -> {
                            showToastMessage(
                                context,
                                "Start time must be before end time",
                                Constants.TOASTERROR
                            )
                        }
                        (endTime - startTime) < 1 -> {
                            showToastMessage(
                                context,
                                "Duration must be at least 1 second",
                                Constants.TOASTERROR
                            )
                        }
                        endTime > durationInSeconds -> {
                            showToastMessage(
                                context,
                                "End time exceeds audio duration",
                                Constants.TOASTERROR
                            )
                        }
                        else -> {
                            audioTrimViewModel.audioTrimmerState(
                                context = context,
                                uri = uri.toUri(),
                                startTime = startTime * 1000,
                                endTime = endTime * 1000,
                                filename = trimmedFilename
                            )
                            showToastMessage(
                                context,
                                "Trimming audio...",
                                Constants.TOASTSUCCESS
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.ContentCut,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Trim Audio",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun TimeDisplay(
    label: String,
    time: String,
    isStart: Boolean
) {
    Column(
        horizontalAlignment = if (isStart) Alignment.Start else Alignment.End
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.alpha(0.6f)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = time,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun UnsupportedDeviceScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ErrorOutline,
                contentDescription = "Not Supported",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Audio Trimmer Not Available",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "This feature requires Android 10 (API 29) or higher.\nYour device is running Android ${Build.VERSION.SDK_INT}.",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.alpha(0.7f),
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
fun ErrorScreen(error: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ErrorOutline,
                contentDescription = "Error",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Something Went Wrong",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = error,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.alpha(0.7f),
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )
        }
    }
}

fun formatTime(seconds: Long): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format(Locale.getDefault(), "%d:%02d", minutes, remainingSeconds)
}
