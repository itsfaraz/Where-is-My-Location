package com.farazsheikh.fusedlocation

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.text.isDigitsOnly
import com.farazsheikh.fusedlocation.service.LocationBeatService
import com.farazsheikh.fusedlocation.service.LocationBeatService.Companion.timeConfig
import com.farazsheikh.fusedlocation.util.AppActivity
import com.farazsheikh.fusedlocation.util.AppInternet
import com.farazsheikh.fusedlocation.util.AppStatus
import com.farazsheikh.fusedlocation.util.ServiceLocator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception

class MainApp : Application() {
    private lateinit var intent: Intent
    override fun onCreate() {
        super.onCreate()
        AppInternet.registerInternetActivity(this)
    }

    @androidx.annotation.RequiresPermission(allOf = [android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION])
    fun activateLocationService(context: Context){
        AppStatus.onAppActivity(AppActivity.OnStarted)
        intent = Intent(context, LocationBeatService::class.java)
        context.startService(intent)
    }



    fun deActivateLocationService(context: Context){
        AppStatus.onAppActivity(AppActivity.OnStopped)
        if (::intent.isInitialized){
            context.stopService(intent)
        }
    }

    fun onConfigChange(timeDelay : String,employeeId : String, ipAddress : String){
        if (timeDelay.isEmpty()){
            LocationBeatService.timeConfig = 5000L
        }else{
            try {
                if (timeDelay.isNotEmpty()){
                    if (timeDelay.isDigitsOnly() && timeDelay.toLong() >= 2000L){
                        LocationBeatService.timeConfig = timeDelay.toLong()
                    }
                }
            }catch (e : Exception){
                Log.i("ERROR", "onEvent: Number Format Exception")
            }finally {
                LocationBeatService.timeConfig = 3000L
            }

        }
        LocationBeatService.employeeId = employeeId
        LocationBeatService.ipAddress = ipAddress
    }

}