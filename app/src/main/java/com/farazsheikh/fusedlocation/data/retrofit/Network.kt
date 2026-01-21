package com.farazsheikh.fusedlocation.data.retrofit

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.farazsheikh.fusedlocation.service.LocationBeatService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Network {
    fun retrofitBuilder(context : Context) : Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .readTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(ChuckerInterceptor(context))
            .build()

        return Retrofit.Builder()
            .baseUrl("http://${LocationBeatService.ipAddress}:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}