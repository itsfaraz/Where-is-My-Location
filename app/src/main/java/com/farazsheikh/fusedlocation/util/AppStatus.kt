package com.farazsheikh.fusedlocation.util

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

object AppStatus {
    val activityStatus : MutableState<String> = mutableStateOf("No Activity")

    fun onAppActivity(event : AppActivity){
        when(event){
            is AppActivity.OnStarted -> {
                activityStatus.value = "Location Tracking Started"
            }
            is AppActivity.OnStopped -> {
                activityStatus.value = "Location Tracking Stopped"
            }
            is AppActivity.InternetOn -> {
                activityStatus.value = "Internet Connectivity Online"
            }
            is AppActivity.InternetOff -> {
                activityStatus.value = "Internet Connectivity Offline"
            }
            is AppActivity.OfflineSyncingStarted -> {
                activityStatus.value = "Offline Syncing Started"
            }
            is AppActivity.OfflineLogsServerUploadingStarted -> {
                activityStatus.value = "Synchronizing With Server"
            }
            is AppActivity.OfflineLogsServerUploadingCompleted -> {
                activityStatus.value = "Successfully Synchronized With Server"
            }
            is AppActivity.LogPosted -> {
                activityStatus.value = "Recent location tracked on ${event.value}"
            }
            is AppActivity.Reset -> {
                activityStatus.value = "No Activity"
            }
        }
    }
}
