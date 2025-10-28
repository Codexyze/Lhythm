package com.example.lhythm.presentation.Screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.example.lhythm.ui.theme.BlackColor
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
    val permission2 = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.POST_NOTIFICATIONS
    } else {
        null
    }
    val permission3 = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        null
    }

    val permissionState = rememberSaveable { mutableStateOf(false) }
    val hasRequestedPermissions = remember { mutableStateOf(false) }

    val launchPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            // Check if ALL required permissions are granted
            val allGranted = permissions.all { it.value }
            permissionState.value = allGranted

            if (!allGranted) {
                FancyToast.makeText(
                    context,
                    "Please grant all permissions to continue",
                    FancyToast.LENGTH_SHORT,
                    FancyToast.CONFUSING,
                    false
                ).show()
            }
        }
    )

    // Check permissions only once on initial composition
    LaunchedEffect(Unit) {
        val hasAudioPermission = ContextCompat.checkSelfPermission(
            context, permission1
        ) == PackageManager.PERMISSION_GRANTED

        val hasNotificationPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && permission2 != null) {
            ContextCompat.checkSelfPermission(
                context, permission2
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true // Not required for older versions
        }

        val hasImagePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && permission3 != null) {
            ContextCompat.checkSelfPermission(
                context, permission3
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true // Not required for older versions
        }

        permissionState.value = hasAudioPermission && hasNotificationPermission && hasImagePermission
    }

    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.musicanimination)
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BlackColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(0.5f))

            // Title
            Text(
                text = "Welcome to Lhythm",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtitle
            Text(
                text = "Lose yourself in music",
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(0.7f)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Lottie Animation
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .size(280.dp)
                    .background(color = Color.Transparent)
            )

            Spacer(modifier = Modifier.weight(1f))

            // Description text
            Text(
                text = "To get started, we need permission to access your music files",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .alpha(0.6f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Get Started Button
            Button(
                onClick = {
                    // Check current permission status
                    val hasAudioPermission = ContextCompat.checkSelfPermission(
                        context, permission1
                    ) == PackageManager.PERMISSION_GRANTED

                    val hasNotificationPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && permission2 != null) {
                        ContextCompat.checkSelfPermission(
                            context, permission2
                        ) == PackageManager.PERMISSION_GRANTED
                    } else {
                        true
                    }

                    val hasImagePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && permission3 != null) {
                        ContextCompat.checkSelfPermission(
                            context, permission3
                        ) == PackageManager.PERMISSION_GRANTED
                    } else {
                        true
                    }

                    val allPermissionsGranted = hasAudioPermission && hasNotificationPermission && hasImagePermission

                    if (allPermissionsGranted) {
                        // All permissions granted, proceed
                        viewmodel.upDateOnBoardingPref()
                        navController.navigate(SAMPLESCREEN) {
                            popUpTo(0)
                        }
                    } else {
                        // Request missing permissions
                        val permissionsToRequest = mutableListOf<String>()
                        if (!hasAudioPermission) permissionsToRequest.add(permission1)

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            if (!hasNotificationPermission && permission2 != null) permissionsToRequest.add(permission2)
                            if (!hasImagePermission && permission3 != null) permissionsToRequest.add(permission3)
                        }

                        if (permissionsToRequest.isNotEmpty()) {
                            hasRequestedPermissions.value = true
                            launchPermission.launch(permissionsToRequest.toTypedArray())
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 32.dp)
            ) {
                Text(
                    text = "Get Started",
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
