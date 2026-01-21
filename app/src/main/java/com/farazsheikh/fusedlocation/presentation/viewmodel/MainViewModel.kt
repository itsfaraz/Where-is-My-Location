package com.farazsheikh.fusedlocation.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import com.farazsheikh.fusedlocation.domain.usecase.AppUseCase
import java.lang.Exception

class MainViewModel(

) : ViewModel() {

    private val _ipAddress : MutableState<String> = mutableStateOf("")
    val ipAddress = _ipAddress


    private val _setting : MutableState<Boolean> = mutableStateOf(false)
    val setting = _setting

    private val _timeConfig : MutableState<String> = mutableStateOf("")
    val timeConfig = _timeConfig

    fun onEvent(event: AppUseCase){
        when(event){
            is AppUseCase.OnSettingToggleEvent -> {
                _setting.value = event.data
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
        }
    }

}