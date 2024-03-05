package com.example.weatherapp.network.api

import com.example.mvvm.api.MyRetrofit
import com.example.weatherapp.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.Query

class RemoteDataSourceImp :RemoteDataSourceInterface {
    private val webService = MyRetrofit.getApi()



    override suspend fun getResponse( lat: String?,
                                     lon: String?,
                                      units: String?,
                                    lang: String?): WeatherResponse {
        return webService.fetchData(lat=lat,lon=lon,units=units,lang=lang).body()!!

    }

    companion object {
        private var instance: RemoteDataSourceImp? = null

        fun getInstance(): RemoteDataSourceImp {
            if (instance == null) {
                instance = RemoteDataSourceImp()
            }
            return instance!!
        }
    }

}