package com.example.weatherapp.model

import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ReposatoryInterface {
    suspend fun getAllResponseList( lat: String?,
                                    lon: String?,
                                    units: String?,
                                    lang: String?): Flow<WeatherResponse>

    fun getFavWeathers(): Flow<List<FavTable>>
    suspend fun insertWeather(product: FavTable)
    suspend fun delete(color: FavTable)

    //Alert

    fun getAlerts(): Flow<List<AlertTable>>
    suspend fun insertAlert(product: AlertTable)
    suspend fun deleteAlert(color: AlertTable)

    //Home

    fun getHomes(): Flow<List<WeatherResponse>>
    suspend fun insertHome(product: WeatherResponse)

}