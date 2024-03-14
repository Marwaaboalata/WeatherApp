package com.example.weatherapp.db.alertdb


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.model.AlertTable
import com.example.weatherapp.model.FavTable
import kotlinx.coroutines.flow.Flow

@Dao
interface AlertDao {

    @Query("SELECT * FROM alertTable")
    fun getAllAlerts():Flow<List<AlertTable>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlert(alert: AlertTable)

    @Delete
    suspend fun deleteAlert(alert: AlertTable)


}




