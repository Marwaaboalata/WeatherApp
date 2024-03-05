package com.example.weatherapp.utils



import com.example.weatherapp.model.WeatherResponse


sealed class ApiState {

    class Success(val list: WeatherResponse): ApiState()
    class Failur(val msg :Throwable): ApiState()
    class Loading(): ApiState()


}