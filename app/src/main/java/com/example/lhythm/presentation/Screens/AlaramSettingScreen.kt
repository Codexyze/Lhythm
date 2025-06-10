package com.example.lhythm.presentation.Screens

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lhythm.constants.Constants
import com.example.lhythm.presentation.Utils.checkPermission
import com.example.lhythm.presentation.Utils.showToastMessage
import com.example.lhythm.presentation.ViewModels.AlaramViewModel

@Composable
fun AlaramSettingScreen(navController: NavController,alaramViewModel: AlaramViewModel= hiltViewModel()) {

    Column(modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        val timevalue = rememberSaveable { mutableStateOf("") }
        val context = LocalContext.current
        val permission = Manifest.permission.USE_EXACT_ALARM
        val permissionlauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            if(it){

            }else{
                showToastMessage(context = context, text = "Please give permission", type = Constants.TOASTERROR)
            }
        }
        LaunchedEffect(Unit) {
            permissionlauncher.launch(permission)
        }
        OutlinedTextField(
            value = timevalue.value,
            onValueChange = {it->
                timevalue.value = it
            },
            label = {
                Text("Search Song")
            },
            modifier = Modifier.fillMaxWidth(0.85f),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                errorTextColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.primary,
            ),textStyle = TextStyle(color = MaterialTheme.colorScheme.primary)
        )
        Button(
            onClick = {
                val permissioncheck=checkPermission(context = context,permission)
                if(permissioncheck) {
                    alaramViewModel.scheduleAlaram(time = ((timevalue.value.toLong() * 1000) + System.currentTimeMillis()))
                }else{
                    permissionlauncher.launch(permission)
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.75f),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Alaram", style = MaterialTheme.typography.titleMedium)
        }


    }

}