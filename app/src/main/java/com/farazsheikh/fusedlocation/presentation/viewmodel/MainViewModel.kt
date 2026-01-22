package com.farazsheikh.fusedlocation.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farazsheikh.fusedlocation.data.retrofit.jdo.LocationLogRequest
import com.farazsheikh.fusedlocation.data.retrofit.jdo.LocationLogResponse
import com.farazsheikh.fusedlocation.domain.repository.LocationLogApiRepository
import com.farazsheikh.fusedlocation.domain.usecase.AppUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(
    private val locationLogApiRepository: LocationLogApiRepository
) : ViewModel() {

    private val _ipAddress : MutableState<String> = mutableStateOf("")
    val ipAddress = _ipAddress


    private val _setting : MutableState<Boolean> = mutableStateOf(false)
    val setting = _setting

    private val _logsState : MutableState<Boolean> = mutableStateOf(false)
    val logsState = _logsState

    private val _timeConfig : MutableState<String> = mutableStateOf("")
    val timeConfig = _timeConfig

    private val _locationLogs = mutableStateListOf<LocationLogRequest>()
    val locationLogs = _locationLogs
    fun onEvent(event: AppUseCase){
        when(event){
            is AppUseCase.OnSettingToggleEvent -> {
                _setting.value = event.data
            }
            is AppUseCase.OnLogsToggleEvent -> {
                _logsState.value = event.data
                if (event.data){
                    fetchLocationLogs()
                }else{
                    clearLocationLogs()
                }
            }
            is AppUseCase.OnIpAddressChange -> {
                _ipAddress.value = event.address
            }
            is AppUseCase.OnTrackingStartEvent -> {
            }
            is AppUseCase.OnTrackingStopEvent -> {
            }
            is AppUseCase.OnElapseTimeChangeEvent -> {
                _timeConfig.value = event.data
            }
            is AppUseCase.OnDeleteLogsEvent -> {
                deleteLocationLogs()
            }
        }
    }

    private fun deleteLocationLogs(){
        viewModelScope.launch(Dispatchers.IO) {
            locationLogApiRepository.deleteAllLocationLog()
        }
    }

    private fun fetchLocationLogs() {
        viewModelScope.launch(Dispatchers.IO) {
            locationLogApiRepository.fetchLocationLogs()?.let {
                _locationLogs.addAll(it)
            }
        }
    }

    private fun clearLocationLogs(){
        _locationLogs.clear()
    }

}