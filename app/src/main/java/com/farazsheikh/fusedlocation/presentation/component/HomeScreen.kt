package com.farazsheikh.fusedlocation.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    elapsedTime : String,
    onElapsedTimeChange : (value : String) -> Unit,
    onStartTrackingEvent : () -> Unit,
    onStopTrackingEvent : () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        TextField(value = elapsedTime, onValueChange = {
            onElapsedTimeChange(it)
        }, placeholder = {
            Text("Elapse Time In Millisecond : ex -> 2000 for 2 sec")
        })
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            onStartTrackingEvent()
        }) {
            Text("Start Tracking")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            onStopTrackingEvent()
        }) {
            Text("Stop Tracking")
        }
    }
}