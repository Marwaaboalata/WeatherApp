package com.example.dayfiveandroidkotlin

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherapp.model.FavTable
import com.example.weatherapp.model.WeatherResponse

@Database(entities = arrayOf (FavTable:: class ), version = 1)
abstract class FavouriteDataBase : RoomDatabase() {
    abstract fun getDao(): FavouriteDao

    companion object {
        @Volatile
        private var INSTANCE: FavouriteDataBase? = null
        fun getInstance(ctx: Context): FavouriteDataBase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    ctx.applicationContext, FavouriteDataBase::class.java, "weather_database")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
//@Database(entities = arrayOf(Color::class), version = 1 )
//abstract class ColorDataBase : RoomDatabase() {
// abstract fun getColorDao(): ColorDao
// companion object{
// @Volatile
// private var INSTANCE: ColorDataBase? = null
// fun getInstance (ctx: Context): ColorDataBase{
// return INSTANCE ?: synchronized(this) {
// val instance = Room.databaseBuilder(
// ctx.applicationContext, ColorDataBase::class.java,
// "color_database")
// .build()
// INSTANCE = instance
//// return instance
// instance }
// }
// }
//}