package com.example.dayfiveandroidkotlin


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.model.FavTable
import com.example.weatherapp.model.WeatherResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {

    @Query("SELECT * FROM favweather")
    fun getAll():Flow<List<FavTable>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: FavTable)

    @Delete
    suspend fun delete(weather: FavTable)


}




/*import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface ProductDao {

//            @Query("SELECT * FROM products_table")
//            suspend fun getAll(): List <ProductsItem>
            @Insert
            suspend fun insert(color : ProductsItem)
            @Delete
            suspend fun delete(color: ProductsItem)
}*/