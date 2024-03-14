package com.example.weatherapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity(tableName = "alert")
//data class AlertTable(var lat:Double,var long:Double,
//                      val time:Long,
//                      val dateStart:Long, val dateEnd:Long)
//
@Entity(tableName = "alertTable")
data class AlertTable(
    @PrimaryKey(autoGenerate = true)
    var alertID:Int=0,
    val startDate: String,
    val endDate: Long ,
    val time: Long,
    val type: String,
    val placeName: String = "",
    val longitude: Double = 0.0,
    val latitude: Double = 0.0,
)