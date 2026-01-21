package com.farazsheikh.fusedlocation.data.retrofit.jdo

data class LocationLogRequest(
    val employeeId : String,
    val latitude : String,
    val longitude : String,
    val accuracy : String,
    val timeStamp : String,
    val speed : String
)
