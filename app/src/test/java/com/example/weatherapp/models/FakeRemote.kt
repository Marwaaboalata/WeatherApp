package com.example.weatherapp.models

import com.example.weatherapp.model.Current
import com.example.weatherapp.model.Weather
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.network.api.RemoteDataSourceImp
import com.example.weatherapp.network.api.RemoteDataSourceInterface

class FakeRemote(var weatherResponse: WeatherResponse) : RemoteDataSourceInterface {
    override suspend fun getResponse(
        lat: String?,
        lon: String?,
        units: String?,
        lang: String?
    ): WeatherResponse {
        return weatherResponse
    }
}
/*
class FakeRemoteSource(private var oneCallResponse: WeatherResponse):RemoteDataSource {
override suspend fun getWeather(
lat: String?,
lon: String?,
lang: String?,
units: String?
): WeatherResponse {
return oneCallResponse
}
}
 */