package com.example.weatherapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "favweather")
data class FavTable (
                 @PrimaryKey(autoGenerate = true)
                  var id: Int=0,
                  val lat: Double,
                  val lon: Double,
                    val city:String="Default"
                    ):java.io.Serializable


