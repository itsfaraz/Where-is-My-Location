package com.farazsheikh.fusedlocation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.farazsheikh.fusedlocation.domain.repository.LogLocationStoreRepository
import com.farazsheikh.fusedlocation.domain.usecase.AppUseCase
import com.farazsheikh.fusedlocation.presentation.component.HomeScreen
import com.farazsheikh.fusedlocation.presentation.component.LocationLogComponent
import com.farazsheikh.fusedlocation.presentation.viewmodel.MainViewModel
import com.farazsheikh.fusedlocation.presentation.viewmodel.MainViewModelFactory
import com.farazsheikh.fusedlocation.ui.theme.FusedLocationTheme
import com.farazsheikh.fusedlocation.util.AppInternet
import com.farazsheikh.fusedlocation.util.AppStatus
import com.farazsheikh.fusedlocation.util.ServiceLocator
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel
    private var mainApp : MainApp? = null

    @androidx.annotation.RequiresPermission(allOf = [android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION])
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainApp = MainApp()
        val locationStoreRepository = ServiceLocator.provideStoreRepository(this)
        val locationApiRepository = ServiceLocator.provideLocationApiRepository(this)
        val factory = MainViewModelFactory(locationApiRepository)
        viewModel = ViewModelProvider.create(this,factory)[MainViewModel::class.java]
        enableEdgeToEdge()
        setContent {
            FusedLocationTheme {
                val setting = viewModel.setting.value
                val logsState = viewModel.logsState.value
                val ipAddress = viewModel.ipAddress.value
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Top
                    ) {
                        Spacer(modifier = Modifier.height(40.dp))
                        HorizontalDivider(modifier = Modifier.fillMaxWidth().height(2.dp), thickness = 8.dp, color = if (AppInternet.isDeviceOnline.value) Color.Green else Color.LightGray)
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            IconButton(
                                modifier = Modifier.padding(start = 10.dp),
                                onClick = {
                                    viewModel.onEvent(AppUseCase.OnSettingToggleEvent(!viewModel.setting.value))
                                }
                            ) {
                                Icon(imageVector = Icons.Default.Settings, contentDescription = "Setting")
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            IconButton(
                                modifier = Modifier.padding(start = 10.dp),
                                onClick = {
                                    viewModel.onEvent(AppUseCase.OnLogsToggleEvent(!viewModel.logsState.value))
                                }
                            ) {
                                Icon(imageVector = Icons.Default.Info, contentDescription = "Location Logs")
                            }
                        }

                        if (setting){
                            Spacer(modifier = Modifier.height(20.dp))
                            TextField(modifier = Modifier.fillMaxWidth().padding(10.dp), value = ipAddress, onValueChange = {
                                viewModel.onEvent(AppUseCase.OnIpAddressChange(it))
                                mainApp?.onConfigChange(viewModel.timeConfig.value,"MP3100",ipAddress)
                            }, placeholder = {
                                Text("Enter Local Ip ex : 192.168.*.*")
                            })
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        HomeScreen(
                            elapsedTime = viewModel.timeConfig.value,
                            onElapsedTimeChange = {
                                viewModel.onEvent(AppUseCase.OnElapseTimeChangeEvent(it))
                                mainApp?.onConfigChange(viewModel.timeConfig.value,"MP3100",ipAddress)
                            },
                            onStartTrackingEvent =  {
                                mainApp?.activateLocationService(this@MainActivity)
                            },
                            onStopTrackingEvent = {
                                mainApp?.deActivateLocationService(this@MainActivity)
                            }
                        )
                    }
                    Text(modifier = Modifier.fillMaxWidth().padding(bottom = 60.dp), text = AppStatus.activityStatus.value, style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.SemiBold), textAlign = TextAlign.Center)
                    if (logsState){
                        Column {
                            LocationLogComponent(logsList = viewModel.locationLogs,onDeleteLogs = {
                                viewModel.onEvent(AppUseCase.OnDeleteLogsEvent)
                            })
                        }
                    }
                }
            }
        }
    }
}

