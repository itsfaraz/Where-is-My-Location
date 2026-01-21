package com.farazsheikh.fusedlocation

import android.location.Location

interface EventLogger {
    suspend fun logEvent(location: Location)
    suspend fun syncDatabaseLog()

    suspend fun clearLogs()
}