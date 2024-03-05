package com.example.mvvm.api


import com.example.weatherapp.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query


//import retrofit2.http.GET

interface ApiService {
//?lat={lat}&lon={lon}&appid=a162cb43de7f315d44dc7173f88e746a

    @GET("onecall")
    suspend fun fetchData(@Query("lat") lat: String? ,
                           @Query("lon") lon: String?,
                           @Query("exclude") exclude: String?="minutely",
                           @Query("units") units: String?,
                           @Query("lang") lang: String?,
                           @Query("appid") appid: String?="f2f9ec409c67b8498f33c2bf4c7fb7e7"): retrofit2.Response<WeatherResponse>
                           }
