package com.farazsheikh.fusedlocation.data.retrofit.jdo

data class LocationLogResponse(
    val status : String? = null,
    val message : String? = null,
    val result : List<LocationLogRequest>? = null
)
