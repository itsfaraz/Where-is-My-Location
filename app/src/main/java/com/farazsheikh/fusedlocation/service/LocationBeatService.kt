package com.farazsheikh.fusedlocation.service

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresPermission
import com.farazsheikh.fusedlocation.EventLogger
import com.farazsheikh.fusedlocation.IEventLogger
import com.farazsheikh.fusedlocation.util.AppActivity
import com.farazsheikh.fusedlocation.util.AppInternet
import com.farazsheikh.fusedlocation.util.AppStatus
import com.farazsheikh.fusedlocation.util.ServiceLocator
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LocationBeatService : Service() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var eventLogger: EventLogger
    private var localScope : CoroutineScope = CoroutineScope(Dispatchers.IO + Job())
    private lateinit var locationCallback: LocationCallback
    private val TAG = "LocationService"

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate: LocationBeatService Created Before Initialization")

        val locationRepository = ServiceLocator.provideLocationApiRepository(this)
        val storeRepository = ServiceLocator.provideStoreRepository(this)
        eventLogger = IEventLogger(storeRepository,locationRepository,employeeId)

        Log.i(TAG, "onCreate: LocationBeatService Created")
        localScope.launch {
            Log.i(TAG, "onCreate: LocationBeatService Scope")
            activateFusedLocation(applicationContext)
        }
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    suspend fun activateFusedLocation(context: Context){
        Log.i(TAG, "activateFusedLocation: ")
        this.beatFusedLocation(context)
    }

    fun deActivateFusedLocation(){
        localScope?.cancel()
        if (::fusedLocationClient.isInitialized){
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    private fun beatFusedLocation(context : Context){
        Log.i(TAG, "beatFusedLocation : LocationBeatService ")
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        val locationRequest = LocationRequest().setInterval(timeConfig).setFastestInterval(timeConfig).setPriority(
            LocationRequest.PRIORITY_HIGH_ACCURACY)
        if (AppInternet.isDeviceOnline.value){
            AppStatus.onAppActivity(AppActivity.InternetOn)
        }else{
            AppStatus.onAppActivity(AppActivity.InternetOff)
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                p0 ?: return
                p0.locations[0]?.let {
                   localScope.launch {
                        eventLogger.logEvent(it)
                    }
                }
            }
        }
        fusedLocationClient.requestLocationUpdates(locationRequest,locationCallback, Looper.getMainLooper())
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: LocationBeatService Destroy")
        deActivateFusedLocation()
        ServiceLocator.cleanServiceLocator()
        AppStatus.onAppActivity(AppActivity.Reset)
    }

    companion object{
        var timeConfig : Long = 3000L
        var employeeId : String = "MP3001"
        var ipAddress : String = "192.168.123.123"
    }
}