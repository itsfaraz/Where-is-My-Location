package com.farazsheikh.fusedlocation.util

import android.content.Context
import com.farazsheikh.fusedlocation.data.database.AppDatabase
import com.farazsheikh.fusedlocation.data.retrofit.Network
import com.farazsheikh.fusedlocation.data.retrofit.api.LocationLogService
import com.farazsheikh.fusedlocation.domain.repository.LocationLogApiRepository
import com.farazsheikh.fusedlocation.domain.repository.LogLocationStoreRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

object ServiceLocator {
    private var locationStoreRepository: LogLocationStoreRepository? = null
    private var locationLogApiRepository: LocationLogApiRepository? = null
    private var globalScope: CoroutineScope? = null

    public fun provideStoreRepository(context: Context) : LogLocationStoreRepository{
        return locationStoreRepository ?: createLogLocationStoreRepository(context)
    }

    private fun createLogLocationStoreRepository(context: Context): LogLocationStoreRepository {
        val locationDao = AppDatabase.registerDatabase(context).locationLogDao()
        locationStoreRepository = LogLocationStoreRepository(locationDao)
        return locationStoreRepository!!
    }

    public fun provideLocationApiRepository(context: Context) : LocationLogApiRepository{
        return locationLogApiRepository ?: createLocationLogApiRepository(context)
    }

    fun createLocationLogApiRepository(context: Context): LocationLogApiRepository {
        val service = Network.retrofitBuilder(context).create(LocationLogService::class.java)
        locationLogApiRepository = LocationLogApiRepository(service)
        return locationLogApiRepository!!
    }

    public fun provideCoroutineScope() : CoroutineScope{
        return globalScope ?: createCoroutineScope()
    }

    fun createCoroutineScope(): CoroutineScope {
        globalScope = CoroutineScope(Dispatchers.IO + Job())
        return globalScope!!
    }

    fun cleanServiceLocator(){
        globalScope?.let { it.cancel() }
        locationStoreRepository = null
        locationLogApiRepository = null
    }
}