package com.nutrino.lhythm.presentation.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nutrino.lhythm.constants.Constants
import com.nutrino.lhythm.presentation.Utils.showToastMessage
import com.nutrino.lhythm.presentation.ViewModels.AlaramViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.util.Locale

@Composable
fun AlaramSettingScreen(
    navController: NavController,
    alaramViewModel: AlaramViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    // Time states
    val hourValue = rememberSaveable { mutableStateOf(0) }
    val minuteValue = rememberSaveable { mutableStateOf(0) }
    val secondValue = rememberSaveable { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = "Alarm",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Set Sleep Timer",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Music will stop after the selected time",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.alpha(0.6f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Time Picker Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Time Picker Wheels
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Hours
                        TimePickerWheel(
                            value = hourValue.value,
                            range = 0..23,
                            label = "Hours",
                            onValueChange = { hourValue.value = it }
                        )

                        Text(
                            text = ":",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        // Minutes
                        TimePickerWheel(
                            value = minuteValue.value,
                            range = 0..59,
                            label = "Min",
                            onValueChange = { minuteValue.value = it }
                        )

                        Text(
                            text = ":",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        // Seconds
                        TimePickerWheel(
                            value = secondValue.value,
                            range = 0..59,
                            label = "Sec",
                            onValueChange = { secondValue.value = it }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Quick Select Buttons
            Text(
                text = "Quick Select",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.alpha(0.7f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                QuickSelectButton("15 min") {
                    hourValue.value = 0
                    minuteValue.value = 15
                    secondValue.value = 0
                }
                QuickSelectButton("30 min") {
                    hourValue.value = 0
                    minuteValue.value = 30
                    secondValue.value = 0
                }
                QuickSelectButton("1 hour") {
                    hourValue.value = 1
                    minuteValue.value = 0
                    secondValue.value = 0
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Set Alarm Button
            Button(
                onClick = {
                    val h = hourValue.value
                    val m = minuteValue.value
                    val s = secondValue.value

                    if (h == 0 && m == 0 && s == 0) {
                        showToastMessage(
                            context,
                            "Please select a time ⏰",
                            Constants.TOASTERROR
                        )
                        return@Button
                    }

                    val totalMillis = ((h * 3600) + (m * 60) + s) * 1000L
                    val finalTime = System.currentTimeMillis() + totalMillis

                    alaramViewModel.scheduleAlaram(finalTime)

                    showToastMessage(
                        context,
                        "Sleep timer set successfully! 🔔",
                        Constants.TOASTSUCCESS
                    )

                    // Reset values
                    hourValue.value = 0
                    minuteValue.value = 0
                    secondValue.value = 0
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Set Timer",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
fun TimePickerWheel(
    value: Int,
    range: IntRange,
    label: String,
    onValueChange: (Int) -> Unit
) {
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = value)

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .map { index -> index.coerceIn(range) }
            .distinctUntilChanged()
            .collect { onValueChange(it) }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.alpha(0.6f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .width(80.dp)
                .height(120.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.background.copy(alpha = 0.3f))
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(range.count()) { index ->
                    val itemValue = range.first + index
                    val isSelected = itemValue == value

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onValueChange(itemValue)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = String.format(Locale.getDefault(), "%02d", itemValue),
                            fontSize = if (isSelected) 28.sp else 20.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f)
                        )
                    }
                }
            }

            // Center indicator
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .align(Alignment.Center)
                    .background(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        RoundedCornerShape(8.dp)
                    )
            )
        }
    }
}

@Composable
fun QuickSelectButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(100.dp)
            .height(44.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
    ) {
        Text(
            text = text,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
