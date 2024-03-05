package com.example.weatherapp.model

import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ReposatoryInterface {
    suspend fun getAllResponseList( lat: String?,
                                    lon: String?,
                                    units: String?,
                                    lang: String?): Flow<WeatherResponse>
}