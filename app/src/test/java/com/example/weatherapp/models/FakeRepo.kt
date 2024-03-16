package com.example.weatherapp.models

import com.example.weatherapp.model.AlertTable
import com.example.weatherapp.model.Current
import com.example.weatherapp.model.FavTable
import com.example.weatherapp.model.ReposatoryInterface
import com.example.weatherapp.model.Weather
import com.example.weatherapp.model.WeatherResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeRepo : ReposatoryInterface {

    var favWeather:MutableList<FavTable>? = mutableListOf(FavTable(0, 5.5, 4.4, "Ism"),FavTable(1, 5.5, 4.4, "Ism"),FavTable(2, 5.5, 4.4, "Ism"))

    var alertsList:List<FavTable> = mutableListOf()
    val remoteSourceResponse = WeatherResponse(
        listOf(),

        Current(

            0, 0.0, 0, 0.0, 0, 0, 0, 0, 0.0, 0.0, 0, listOf<Weather>(), 0, 0.0

        ), listOf(), listOf(), 0.0, 0.0, "", 0

    )

    override suspend fun getAllResponseList(
        lat: String?,
        lon: String?,
        units: String?,
        lang: String?
    ): Flow<WeatherResponse> {
   return flowOf(remoteSourceResponse)   }

    override fun getFavWeathers(): Flow<List<FavTable>> {
//        favWeather?.add(FavTable(0, 5.5, 4.4, "Ism"))
//        favWeather?.add(FavTable(1, 5.5, 4.4, "Ism"))
//        favWeather?.add(FavTable(2, 5.5, 4.4, "Ism"))

        return flowOf(favWeather as List<FavTable>)
    }

    override suspend fun insertWeather(product: FavTable) {
        favWeather?.add(product)

    }

    override suspend fun delete(color: FavTable) {
            favWeather?.remove(color)
    }

    override fun getAlerts(): Flow<List<AlertTable>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertAlert(product: AlertTable) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAlert(color: AlertTable) {
        TODO("Not yet implemented")
    }
}