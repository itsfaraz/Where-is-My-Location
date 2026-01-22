package com.farazsheikh.fusedlocation.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farazsheikh.fusedlocation.data.retrofit.jdo.LocationLogRequest

@Composable
fun LocationLogComponent(
    logsList : List<LocationLogRequest>,
    onDeleteLogs : () -> Unit
) {

    Spacer(modifier = Modifier.height(100.dp))
    LazyColumn(modifier = Modifier.padding(10.dp).fillMaxSize().background(color = Color.White).padding(10.dp)) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth().height(15.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = {
                    onDeleteLogs()
                }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                }
            }
        }
        items(logsList.size){ index ->
            logsList[index]?.let { log ->
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.height(20.dp).fillMaxWidth().background(color = Color.LightGray)
                ) {
                    Text("Lat : ${log.latitude} :: Long : ${log.longitude} :: accuracy : ${log.accuracy} :: speed : ${log.speed}", textAlign = TextAlign.Center, style = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.SemiBold))
                }
                HorizontalDivider(modifier = Modifier.height(2.dp), color = Color.Black, thickness = 2.dp)
            }
        }
    }

}