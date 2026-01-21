package com.farazsheikh.fusedlocation.data.retrofit.api

import com.farazsheikh.fusedlocation.data.retrofit.jdo.LocationLogRequest
import com.farazsheikh.fusedlocation.data.retrofit.jdo.LocationLogResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface LocationLogService {
    @GET("/mock/fused")
    suspend fun fetchLocationLogs() : Response<LocationLogResponse>

    @POST("/mock/fused")
    suspend fun postLocationLog(@Body locationLogRequest: LocationLogRequest) : Response<LocationLogResponse>

    @DELETE("/mock/fused")
    suspend fun deleteLocationLog(locationLogRequest: LocationLogRequest) : Response<LocationLogResponse>
}