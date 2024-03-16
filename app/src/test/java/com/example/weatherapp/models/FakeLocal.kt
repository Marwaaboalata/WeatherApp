package com.example.weatherapp.models

import com.example.weatherapp.db.LocalDataSourceInterface
import com.example.weatherapp.model.AlertTable
import com.example.weatherapp.model.FavTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeLocal(
    private var favList: MutableList<FavTable> = mutableListOf()

) : LocalDataSourceInterface {
    override fun getAll(): Flow<List<FavTable>> {
        return flowOf(favList)
    }

    override suspend fun insertWeather(product: FavTable) {
        favList.add(product)
    }

    override suspend fun delete(color: FavTable) {
        favList.remove(color)
    }

    override fun getAllAlerts(): Flow<List<AlertTable>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertAlert(product: AlertTable) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAlert(color: AlertTable) {
        TODO("Not yet implemented")
    }
}
/*
class FakeLocalSource(
private var favList: MutableList<FavoriteCity> = mutableListOf()
):LocalDataSource {
override suspend fun getAllFavCity(): Flow<List<FavoriteCity>> {
return flowOf(favList)
}
override suspend fun insertCity(favoriteCity: FavoriteCity) {
favList.add(favoriteCity)
}
override suspend fun deleteCity(favoriteCity: FavoriteCity) {
favList.remove(favoriteCity)
}
override suspend fun insertIntoAlert(alertDto: AlertDto) {
TODO("Not yet implemented")
}
override suspend fun removeFromAlerts(alertDto: AlertDto) {
TODO("Not yet implemented")
}
override suspend fun getAlerts(): Flow<List<AlertDto>> {
TODO("Not yet implemented")
}
}
 */