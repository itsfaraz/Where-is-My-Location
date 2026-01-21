package com.farazsheikh.fusedlocation.util

import android.location.Location

sealed class AppActivity{
    object OnStarted : AppActivity()
    object InternetOn : AppActivity()
    object InternetOff : AppActivity()
    object OfflineSyncingStarted : AppActivity()
    object OfflineLogsServerUploadingStarted : AppActivity()
    object OfflineLogsServerUploadingCompleted : AppActivity()
    data class LogPosted(val value : String) : AppActivity()
    object OnStopped : AppActivity()
    object Reset : AppActivity()
}