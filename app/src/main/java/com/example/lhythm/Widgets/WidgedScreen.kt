package com.example.lhythm.Widgets



import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import com.example.lhythm.R
import com.example.lhythm.presentation.Screens.MainActivity


@Composable
fun WidgetScreen() {
    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .padding(16.dp),

    ) {
        Box(modifier = GlanceModifier.fillMaxSize().clickable(onClick = actionStartActivity<MainActivity>())
        ) {
            Image(
                provider = ImageProvider(R.drawable.lythmlogoasset), // put your music logo in res/drawable
                contentDescription = "Music Logo",
                modifier = GlanceModifier.fillMaxSize() // adjust size of the icon
            )

        }

    }
}

