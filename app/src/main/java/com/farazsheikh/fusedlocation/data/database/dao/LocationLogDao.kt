package com.farazsheikh.fusedlocation.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.farazsheikh.fusedlocation.data.database.entity.LocationLog
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationLogDao {
    @Insert
    suspend fun storeLog(locationLog: LocationLog)

    @Query("SELECT * FROM LocationLog")
    suspend fun readLogs() : List<LocationLog>

    @Query("DELETE FROM locationlog")
    suspend fun deleteAllLogs()

    @Query("SELECT (SELECT COUNT(*) FROM LocationLog) == 0")
    suspend fun isEmpty(): Boolean
}
