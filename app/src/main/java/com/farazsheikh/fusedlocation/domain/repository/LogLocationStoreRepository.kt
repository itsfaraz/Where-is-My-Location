package com.farazsheikh.fusedlocation.domain.repository

import com.farazsheikh.fusedlocation.data.database.dao.LocationLogDao
import com.farazsheikh.fusedlocation.data.database.entity.LocationLog

class LogLocationStoreRepository(
    private val locationLogDao: LocationLogDao
) {

    suspend fun storeLog(locationLog: LocationLog) = locationLogDao.storeLog(locationLog)

    suspend fun readLogs() : List<LocationLog> = locationLogDao.readLogs()

    suspend fun deleteAllLogs() = locationLogDao.deleteAllLogs()

    suspend fun isDatabaseEmpty() = locationLogDao.isEmpty()
}