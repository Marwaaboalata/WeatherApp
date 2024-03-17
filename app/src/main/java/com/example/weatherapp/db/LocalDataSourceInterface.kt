package com.example.weatherapp.db

import com.example.weatherapp.model.AlertTable
import com.example.weatherapp.model.FavTable
import com.example.weatherapp.model.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface LocalDataSourceInterface {
    fun getAll(): Flow<List<FavTable>>
    suspend fun insertWeather(product: FavTable)
    suspend fun delete(color: FavTable)


    //Alert
    fun getAllAlerts(): Flow<List<AlertTable>>
    suspend fun insertAlert(product: AlertTable)
    suspend fun deleteAlert(color: AlertTable)


    //Home

    fun getHomes(): Flow<List<WeatherResponse>>
    suspend fun insertHome(product: WeatherResponse)


}