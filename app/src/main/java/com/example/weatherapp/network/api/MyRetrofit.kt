package com.example.mvvm.api

import com.example.weatherapp.utils.Constant
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object MyRetrofit {
    const val BASE_URL = Constant.Base_URL

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