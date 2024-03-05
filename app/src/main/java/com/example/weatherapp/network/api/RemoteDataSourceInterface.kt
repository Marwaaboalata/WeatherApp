package com.example.weatherapp.network.api


import com.example.weatherapp.model.WeatherResponse
import retrofit2.Response

interface RemoteDataSourceInterface {
   suspend fun getResponse( lat: String?,
                            lon: String?,
                            units: String?,
                            lang: String?): WeatherResponse
}