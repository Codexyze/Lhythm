package com.example.lhythm.presentation.Screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.shashank.sony.fancytoastlib.FancyToast


@Composable
fun OnBoardingScreen(
    viewmodel: OnBoardingViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    val permission1 = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
        Manifest.permission.READ_MEDIA_AUDIO
    }else{
        Manifest.permission.READ_EXTERNAL_STORAGE
    }
    val permission2 = Manifest.permission.POST_NOTIFICATIONS




    val permissionState = rememberSaveable { mutableStateOf(false) }
    val launchPermission = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()
        , onResult = {
            it.entries.forEach{
                if(it.value){
                    permissionState.value = true
                }else{
                    permissionState.value = false
                    FancyToast.makeText(
                        context, "Grant all permission",
                        FancyToast.LENGTH_SHORT,
                        FancyToast.CONFUSING, false
                    ).show()
                }

            }
        })

    LaunchedEffect(permissionState.value) {
        if(ContextCompat.checkSelfPermission(context,permission1)== PackageManager.PERMISSION_GRANTED){

        }else{
            launchPermission.launch(arrayOf(permission2,permission2))
            FancyToast.makeText(
                context, "Grant all permissions",
                FancyToast.LENGTH_SHORT,
                FancyToast.WARNING, false
            ).show()
        }
    }

    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.musicanimination)
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

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
                    "Lose yourself in music",
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
                                permissionState.value = checkPermission(context = context,permission=permission1)
                                if(permissionState.value){
                                    viewmodel.upDateOnBoardingPref()
                                    navController.navigate(SAMPLESCREEN) {
                                        popUpTo(0)
                                    }
                                }else{
                                   launchPermission.launch(arrayOf(permission1,permission2))

                                }

                            }, colors = ButtonDefaults.buttonColors(containerColor = RedThemeSuit1)) {
                                Text("getting Started")
                            }
            }


    }


