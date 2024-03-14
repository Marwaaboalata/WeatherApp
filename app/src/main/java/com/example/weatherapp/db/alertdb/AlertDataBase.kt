package com.example.weatherapp.db.alertdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherapp.model.AlertTable
import com.example.weatherapp.model.FavTable

@Database(entities = arrayOf (AlertTable:: class ), version = 1)
abstract class AlertDataBase : RoomDatabase() {
    abstract fun getDao(): AlertDao

    companion object {
        @Volatile
        private var INSTANCE: AlertDataBase? = null
        fun getInstance(ctx: Context): AlertDataBase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    ctx.applicationContext, AlertDataBase::class.java, "alert_database")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
