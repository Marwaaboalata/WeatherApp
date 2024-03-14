package com.example.weatherapp.db

import android.content.Context
import com.example.dayfiveandroidkotlin.FavouriteDao
import com.example.dayfiveandroidkotlin.FavouriteDataBase
import com.example.weatherapp.db.alertdb.AlertDao
import com.example.weatherapp.db.alertdb.AlertDataBase
import com.example.weatherapp.model.AlertTable
import com.example.weatherapp.model.FavTable
import com.example.weatherapp.model.WeatherResponse
import kotlinx.coroutines.flow.Flow

class LocalDataSourceImp(var contxt: Context):LocalDataSourceInterface {
    val weatherDao : FavouriteDao = FavouriteDataBase.getInstance(contxt).getDao()
    val alertDao : AlertDao = AlertDataBase.getInstance(contxt).getDao()

    override fun getAll(): Flow<List<FavTable>> {
            return weatherDao.getAll()   }

    override suspend fun insertWeather(weather: FavTable) {
            weatherDao.insertWeather(weather)
    }

    override suspend fun delete(weather: FavTable) {
        weatherDao.delete(weather)
    }

    override fun getAllAlerts(): Flow<List<AlertTable>> {
        return alertDao.getAllAlerts()
    }

    override suspend fun insertAlert(product: AlertTable) {
            alertDao.insertAlert(product)    }

    override suspend fun deleteAlert(color: AlertTable) {
            alertDao.deleteAlert(color)    }


}