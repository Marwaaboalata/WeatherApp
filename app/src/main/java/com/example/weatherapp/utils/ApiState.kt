package com.example.weatherapp.utils



import com.example.weatherapp.model.WeatherResponse


sealed class ApiState<out T> {

    class Success<T>(val data: T): ApiState<T>()
    class Failur(val msg :Throwable): ApiState<Nothing>()
    class Loading(): ApiState<Nothing>()


}