package com.example.mvvm.dao


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.dayfiveandroidkotlin.FavouriteDataBase
import com.example.weatherapp.db.LocalDataSourceImp
import com.example.weatherapp.model.FavTable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class TasksLocalDataSource {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    lateinit var database : FavouriteDataBase
    lateinit var localDataSource: LocalDataSourceImp

    @Before
    fun setUp(){
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),FavouriteDataBase::class.java
        )
            .allowMainThreadQueries()
            .build()
        localDataSource = LocalDataSourceImp(ApplicationProvider.getApplicationContext())
            //LocalDataSourceImpl(database.getProductDao()
        //)
    }
    @After
    fun closeDb(){
        database.close()
    }
    @Test
    fun insertProduct() = runBlockingTest {
        //Given
        val product = FavTable(2,5.5,4.4,"Ism")

        //when
        localDataSource.insertWeather(product)
        //Then
        val result = localDataSource.getAll().first()

        assertThat(result[0].id,`is`(product.id))

    }
    @Test
    fun deleteProduct() = runBlockingTest {
        // Given
        val product = FavTable(0,5.5,4.4,"Ism")

        // When
        localDataSource.delete(product)

        database.getDao().delete(product)

        // Then
        val result = localDataSource.getAll().first()
        assertThat(result.isEmpty(),`is`(true))

    }

    @Test
    fun getAllFav() = runBlockingTest {
        //Given
        val product1 = FavTable(1,5.5,4.4,"Ism")
        val product2 = FavTable(2,5.5,4.4,"Ism")
        val product3 = FavTable(3,5.5,4.4,"Ism")

        //when
        localDataSource.insertWeather(product1)
        localDataSource.insertWeather(product2)
        localDataSource.insertWeather(product3)

        //Then
        val result = localDataSource.getAll().first()

        assertThat(result.size,`is`(3))

    }



}
