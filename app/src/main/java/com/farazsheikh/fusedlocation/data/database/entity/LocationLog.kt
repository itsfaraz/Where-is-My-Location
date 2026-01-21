package com.farazsheikh.fusedlocation.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationLog (

    @ColumnInfo(name = "employeeId")
    val employeeId : String,
    @ColumnInfo(name = "latitude")
    val latitude : String,
    @ColumnInfo(name = "longitude")
    val longitude : String,
    @ColumnInfo(name = "accuracy")
    val accuracy : String,
    @PrimaryKey
    @ColumnInfo(name = "timestamp")
    val timeStamp : String,
    @ColumnInfo(name = "speed")
    val speed : String
)