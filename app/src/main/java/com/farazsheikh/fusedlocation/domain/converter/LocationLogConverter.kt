package com.farazsheikh.fusedlocation.domain.converter

import android.location.Location
import com.farazsheikh.fusedlocation.data.database.entity.LocationLog
import com.farazsheikh.fusedlocation.data.retrofit.jdo.LocationLogRequest

object LocationLogConverter {
    fun getLocationLogRequest(employeeId : String, location: Location) : LocationLogRequest{
        return LocationLogRequest(
            employeeId = employeeId,
            latitude = location.latitude.toString(),
            longitude = location.longitude.toString(),
            accuracy = location.accuracy.toString(),
            timeStamp = location.time.toString(),
            speed = location.speed.toString()
        )
    }

    fun getLocationLogEntity(employeeId : String, location: Location) : LocationLog{
        return LocationLog(
            employeeId = employeeId,
            latitude = location.latitude.toString(),
            longitude = location.longitude.toString(),
            accuracy = location.accuracy.toString(),
            timeStamp = location.time.toString(),
            speed = location.speed.toString()
        )
    }

    fun getLocationLogRequest(employeeId : String, location: LocationLog) : LocationLogRequest{
        return LocationLogRequest(
            employeeId = employeeId,
            latitude = location.latitude,
            longitude = location.longitude,
            accuracy = location.accuracy,
            timeStamp = location.timeStamp,
            speed = location.speed
        )
    }
}