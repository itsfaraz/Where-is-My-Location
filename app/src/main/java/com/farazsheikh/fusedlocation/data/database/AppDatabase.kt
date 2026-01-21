package com.farazsheikh.fusedlocation.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.farazsheikh.fusedlocation.data.database.dao.LocationLogDao
import com.farazsheikh.fusedlocation.data.database.entity.LocationLog

@Database(entities = arrayOf(LocationLog::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun locationLogDao() : LocationLogDao

    companion object{
        private val DB = "AppDatabase.db"
        fun registerDatabase(context: Context) : AppDatabase{
            return Room.databaseBuilder(context, AppDatabase::class.java,DB).build()
        }
    }

}