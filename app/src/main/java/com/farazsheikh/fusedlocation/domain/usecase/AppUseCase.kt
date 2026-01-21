package com.farazsheikh.fusedlocation.domain.usecase

sealed class AppUseCase {
    data class OnElapseTimeChangeEvent(val data : String) : AppUseCase()
    data class OnIpAddressChange(val address : String) : AppUseCase()
    data class OnSettingToggleEvent(val data : Boolean) : AppUseCase()
    object OnTrackingStartEvent : AppUseCase()
    object OnTrackingStopEvent : AppUseCase()
}