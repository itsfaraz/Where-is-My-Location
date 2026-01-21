package com.farazsheikh.fusedlocation.domain.repository

import android.util.Log
import com.farazsheikh.fusedlocation.data.retrofit.api.LocationLogService
import com.farazsheikh.fusedlocation.data.retrofit.jdo.LocationLogRequest


class LocationLogApiRepository(
    private val locationLogService: LocationLogService
) {
    private val TAG = "LocationLogApiRepository"
    suspend fun fetchLocationLogs() : List<LocationLogRequest>? {
        try {
            val result = locationLogService.fetchLocationLogs()
            if (result.isSuccessful){
                result.body()?.let {
                    if (it.status.equals("200") && it.message.equals("completed")){
                        return it.result!!
                    }
                }

            }
        }catch (e : Exception){
            Log.i(TAG, "uploadLocationLog: Network Error")
        }

        return null
    }

    suspend fun uploadLocationLog(locationLogRequest: LocationLogRequest) : Boolean{
        try {
            val result = locationLogService.postLocationLog(locationLogRequest)
            if (result.isSuccessful){
                result.body()?.let {
                    if (it.status.equals("200") && it.message.equals("completed")){
                        return true
                    }
                }
            }
        }catch (e : Exception){
            Log.i(TAG, "uploadLocationLog: Network Error")
        }
        return false
    }

    suspend fun deleteLocationLog(locationLogRequest: LocationLogRequest) : Boolean{
        try{
            val result = locationLogService.deleteLocationLog(locationLogRequest)
            if (result.isSuccessful){
                result.body()?.let {
                    if (it.status.equals("200") && it.message.equals("completed")){
                        return true
                    }
                }
            }
        }catch (e : Exception){
            Log.i(TAG, "uploadLocationLog: Network Error")
        }

        return false
    }
}