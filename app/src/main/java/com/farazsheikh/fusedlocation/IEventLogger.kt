package com.farazsheikh.fusedlocation

import android.location.Location
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.farazsheikh.fusedlocation.data.retrofit.jdo.LocationLogRequest
import com.farazsheikh.fusedlocation.domain.converter.LocationLogConverter
import com.farazsheikh.fusedlocation.domain.repository.LocationLogApiRepository
import com.farazsheikh.fusedlocation.domain.repository.LogLocationStoreRepository
import com.farazsheikh.fusedlocation.util.AppActivity
import com.farazsheikh.fusedlocation.util.AppInternet
import com.farazsheikh.fusedlocation.util.AppStatus

class IEventLogger(
    private val locationStoreRepository: LogLocationStoreRepository,
    private val locationLogApiRepository: LocationLogApiRepository,
    private val employeeId : String
) : EventLogger {

    private val TAG = "EventLogger"

    private val pendingLogsQueue = mutableListOf<Location>()
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    private suspend fun deviceStateOnline(location: Location) {

        val isEmpty = locationStoreRepository.isDatabaseEmpty()
        if (isEmpty){
            // Quick Push
            AppStatus.onAppActivity(AppActivity.LogPosted(location.time.toString()))
            locationLogApiRepository.uploadLocationLog(LocationLogConverter.getLocationLogRequest(employeeId,location))
        }else{
            // Synchronize Database With Server
            pendingLogsQueue.add(0,location)
            syncDatabaseLog()
        }

    }

    private suspend fun deviceStateOffline(location: Location) {
        AppStatus.onAppActivity(AppActivity.OfflineSyncingStarted)
        locationStoreRepository.storeLog(LocationLogConverter.getLocationLogEntity(employeeId,location))
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override suspend fun logEvent(location: Location) {
        Log.i(TAG, "logEvent: EventLogger ")
        if (AppInternet.isDeviceOnline.value){
            this.deviceStateOnline(location)
        }else{
            this.deviceStateOffline(location)
        }
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override suspend fun syncDatabaseLog() {
        AppStatus.onAppActivity(AppActivity.OfflineLogsServerUploadingStarted)
        val readAllLogs = locationStoreRepository.readLogs().toList().sortedBy { it.timeStamp }
        readAllLogs.forEach { log ->
            log?.let { locationLog ->
                val log = LocationLogConverter.getLocationLogRequest(employeeId,locationLog)
                locationLogApiRepository.uploadLocationLog(log)
            }
        }
        locationStoreRepository.deleteAllLogs()
        while (!pendingLogsQueue.isEmpty()){
            val location = pendingLogsQueue.removeAt(pendingLogsQueue.size-1)
            locationLogApiRepository.uploadLocationLog(LocationLogConverter.getLocationLogRequest(employeeId,location))
        }
        AppStatus.onAppActivity(AppActivity.OfflineLogsServerUploadingCompleted)
    }

    override suspend fun clearLogs() {
        locationStoreRepository.deleteAllLogs()
    }
}