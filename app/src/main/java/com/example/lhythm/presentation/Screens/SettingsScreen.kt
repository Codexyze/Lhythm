package com.example.lhythm.presentation.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lhythm.constants.Constants
import com.example.lhythm.presentation.Navigation.SOUNDFXSCREEN
import com.example.lhythm.presentation.ViewModels.OnBoardingViewModel

@Composable
fun SettingScreen(
    navController: NavController,
    themeSelectionViewModel: OnBoardingViewModel = hiltViewModel()
) {
    val themeSelection = rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Button(
            onClick = {
                themeSelection.value = true
            },
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .height(60.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Choose Theme", style = MaterialTheme.typography.titleMedium)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                navController.navigate(SOUNDFXSCREEN)
            },
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .height(60.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Sound FX", style = MaterialTheme.typography.titleMedium)
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (themeSelection.value) {
            AlertDialog(
                onDismissRequest = {
                    themeSelection.value = false
                },
                confirmButton = {
                    TextButton(onClick = {
                        themeSelection.value = false
                    }) {
                        Text("Close")
                    }
                },
                title = {
                    Text("Change Theme")
                },
                text = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Pick your favorite color:", style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            ThemeCircleButton(color = Color.Blue) {
                                themeSelectionViewModel.updateThemeSelection(Constants.BLUETHEME)
                            }
                            ThemeCircleButton(color = Color.Red) {
                                themeSelectionViewModel.updateThemeSelection(Constants.REDTHEME)
                            }
                            ThemeCircleButton(color = Color.Green) {
                                themeSelectionViewModel.updateThemeSelection(Constants.GREENTHEME)
                            }
                            ThemeCircleButton(color = Color.Yellow) {
                                themeSelectionViewModel.updateThemeSelection(Constants.YELLOWTHEME)
                            }

                        }
                        Row {
                            ThemeCircleButton(color =Color(0xFFDF77EE)) {
                                themeSelectionViewModel.updateThemeSelection(Constants.PURPLETHEME)
                            }
                            ThemeCircleButton(color =Color(0xFFF35389)) {
                                themeSelectionViewModel.updateThemeSelection(Constants.PINKTHEME)
                            }
                            ThemeCircleButton(color = Color(0xFFF54E1B)) {
                                themeSelectionViewModel.updateThemeSelection(Constants.ORANGETHEME)
                            }
                        }
                    }
                }
            )
        }
    }
}
@Composable
fun ThemeCircleButton(color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(60.dp),
        shape = ButtonDefaults.shape,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        contentPadding = PaddingValues(0.dp)
    ) {

    }
}
