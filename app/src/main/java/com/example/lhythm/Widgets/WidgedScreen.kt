package com.example.lhythm.Widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceModifier
import androidx.glance.action.actionStartActivity
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.text.Text
import com.example.lhythm.presentation.Screens.MainActivity

@Composable
fun WidgetScreen() {
    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .padding(16.dp),
        verticalAlignment = Alignment.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Where to?" )

        Row(
            modifier = GlanceModifier.padding(top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                text = "Home",
                onClick = actionStartActivity<MainActivity>() // opens app
            )
            Spacer(GlanceModifier.width(8.dp))
            Button(
                text = "Work",
                onClick = actionStartActivity<MainActivity>() // or another activity
            )
        }
    }
}
