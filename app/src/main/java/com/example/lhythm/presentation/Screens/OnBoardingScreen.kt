package com.example.lhythm.presentation.Screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.lhythm.presentation.Navigation.SAMPLESCREEN
import com.example.lhythm.presentation.ViewModels.OnBoardingViewModel
import com.example.lhythm.R
import com.example.lhythm.presentation.Utils.checkPermission
import com.example.lhythm.ui.theme.BlackColor
import com.example.lhythm.ui.theme.RedThemeSuit1

@Composable
fun OnBoardingScreen(
    viewmodel: OnBoardingViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    val permission = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
        Manifest.permission.READ_MEDIA_AUDIO
    }else{
        Manifest.permission.READ_EXTERNAL_STORAGE
    }
    val launchPermission = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()
    , onResult = {
        if (it){
            Toast.makeText(context, "Permission Granted" , Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Please grant Permission", Toast.LENGTH_SHORT).show()
        }
        })

    val permissionState = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(permissionState.value) {
        if(ContextCompat.checkSelfPermission(context,permission)== PackageManager.PERMISSION_GRANTED){
            Toast.makeText(context, "PermissionGranted", Toast.LENGTH_SHORT).show()
        }else{
            launchPermission.launch(permission)
        }
    }

    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.musicanimination)
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    BoxWithConstraints(modifier = Modifier
        .fillMaxSize()
        .background(color = BlackColor)) {this
        val screenWidth = maxWidth
        if(screenWidth>=400.dp && screenWidth<600.dp ){
            //regular mid phone
                      Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(color = BlackColor),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Welcome to Lhythm", fontSize = 35.sp , fontFamily = FontFamily.Cursive , color = RedThemeSuit1)
                Text(
                    screenWidth.toString(),
                    fontSize = 35.sp,
                    fontFamily = FontFamily.Cursive,
                    color = Color.Green
                )

                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier
                        .size(250.dp)
                        .background(color = Color.Black)
                )

                Spacer(modifier = Modifier.height(16.dp))
                          Button(onClick = {
                              permissionState.value = checkPermission(context = context,permission=permission)
                              if(permissionState.value){
                                  viewmodel.upDateOnBoardingPref()
                                  navController.navigate(SAMPLESCREEN) {
                                      popUpTo(0)
                                  }
                              }else{
                                  launchPermission.launch(permission)
                                  Toast.makeText(context, "Please grant Permission", Toast.LENGTH_SHORT).show()
                              }

                          }, colors = ButtonDefaults.buttonColors(containerColor = RedThemeSuit1)) {
                              Text("getting Started")
                          }
            }
        }else if(screenWidth < 400.dp){
                        Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .background(color = BlackColor),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Welcome to Lhythm",
                    fontSize = 24.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color.Green
                )
                Text(
                    screenWidth.toString(),
                    fontSize = 24.sp,
                    fontFamily = FontFamily.Cursive,
                    color = Color.Green
                )

                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier
                        .size(200.dp)
                        .background(color = Color.Black)
                )

                Spacer(modifier = Modifier.height(16.dp))

                            Button(onClick = {
                                permissionState.value = checkPermission(context = context,permission=permission)
                                if(permissionState.value){
                                    viewmodel.upDateOnBoardingPref()
                                    navController.navigate(SAMPLESCREEN) {
                                        popUpTo(0)
                                    }
                                }else{
                                    launchPermission.launch(permission)
                                    Toast.makeText(context, "Please grant Permission", Toast.LENGTH_SHORT).show()
                                }

                            }, colors = ButtonDefaults.buttonColors(containerColor = RedThemeSuit1)) {
                                Text("getting Started")
                            }
            }
        }else if(screenWidth >=600.dp){

                        Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                // Tablet: Bigger layout, maybe add more stuff here?
                Text("Welcome to Lhythm", fontSize = 35.sp , fontFamily = FontFamily.Cursive , color = Color(0xFFFF0B55))
                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier
                        .size(450.dp)
                        .background(Color.Black)
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {


                    Button(onClick = {
                        permissionState.value = checkPermission(context = context,permission=permission)
                        if(permissionState.value){
                            viewmodel.upDateOnBoardingPref()
                            navController.navigate(SAMPLESCREEN) {
                                popUpTo(0)
                            }
                        }else{
                            launchPermission.launch(permission)
                            Toast.makeText(context, "Please grant Permission", Toast.LENGTH_SHORT).show()
                        }

                    }, colors = ButtonDefaults.buttonColors(containerColor = RedThemeSuit1)) {
                        Text("getting Started")
                    }
                }
            }

        }


    }

}

