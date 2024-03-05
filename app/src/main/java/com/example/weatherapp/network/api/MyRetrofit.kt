package com.example.mvvm.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object MyRetrofit {
    const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    fun getRetrofitInstance(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.
                create(GsonBuilder().create())).build()
    }
    fun getApi(): ApiService {
        return getRetrofitInstance().create(ApiService::class.java)
    }
}