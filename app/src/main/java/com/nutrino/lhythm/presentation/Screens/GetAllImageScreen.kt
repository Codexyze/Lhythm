package com.nutrino.lhythm.presentation.Screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.nutrino.lhythm.presentation.Navigation.IMAGETOSONGMAPSCREEN
import com.nutrino.lhythm.presentation.ViewModels.ImageViewModel

@Composable
fun ImageScreen(
    viewModel: ImageViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.getAllImageState.collectAsState()

    // Call getAllImage() when screen first loads
    LaunchedEffect(Unit) {
        Log.d("ImageScreen", "LaunchedEffect triggered → calling getAllImage()")
        viewModel.getAllImage()
    }

    when {
        state.isLoading -> {
            Log.d("ImageScreen", "UI state: Loading images...")
            Text("Loading images...")
        }
        state.error != null -> {
            Log.e("ImageScreen", "UI state: Error → ${state.error}")
            Text("Error: ${state.error}")
        }
        else -> {
            Log.d("ImageScreen", "UI state: Success → Showing ${state.data.size} images")

            // ✅ Display 2 images per row
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background),
                contentPadding = PaddingValues(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(state.data) { image ->
                    Log.d("ImageScreen", "Binding image: name=${image.name}, uri=${image.path}")

                    AsyncImage(
                        model = image.path.toUri(), // we stored contentUri.toString()
                        contentDescription = image.name,
                        modifier = Modifier
                            .fillMaxWidth().aspectRatio(1f).clickable{
                                navController.navigate(IMAGETOSONGMAPSCREEN(Imgpath = image.path))

                            }
                             // keep square
                    )
                }
            }
        }
    }
}