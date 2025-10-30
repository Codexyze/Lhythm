package com.nutrino.lhythm.presentation.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nutrino.lhythm.presentation.Navigation.GETALLIMAGESCREEN
import com.nutrino.lhythm.presentation.Navigation.VIEWMEMORYSCREEN

@Composable
fun MemoryBoxSelectionScreen(navController: NavController) {
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().padding(it)
            .background(MaterialTheme.colorScheme.background), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(36.dp))

            Button(
                onClick = {
                    navController.navigate(GETALLIMAGESCREEN)

                },
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .height(60.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Add Memory", style = MaterialTheme.typography.titleMedium)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    navController.navigate(VIEWMEMORYSCREEN)

                },
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .height(60.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("View Memory", style = MaterialTheme.typography.titleMedium)
            }

        }


    }

}

