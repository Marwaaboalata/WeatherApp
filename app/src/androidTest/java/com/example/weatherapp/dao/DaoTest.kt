package com.example.mvvm.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.dayfiveandroidkotlin.FavouriteDataBase
import com.example.weatherapp.model.FavTable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class TasksDaoTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    lateinit var database : FavouriteDataBase


    @Before
    fun setUp(){
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            FavouriteDataBase::class.java).build()

    }

    @After
    fun end(){
        database.close()
    }


    @Test
    fun InsertIntoDao ()= runBlockingTest{

        //Given
        val task = FavTable(0,5.5,4.4,"Ism")
            database.getDao().insertWeather(task)

        //when
        val loaded = database.getDao().getAll().first()

        //Then
        assertThat(loaded.get(0) as FavTable,notNullValue())
        assertThat(loaded.first().city,`is`(task.city))
        assertThat(loaded.first().lat,`is`(task.lat))


    }


    @Test
    fun delete()= runBlockingTest{

        //Given
        val task = FavTable(0,5.5,4.4,"Ism")
        val task1 = FavTable(1,5.5,4.4,"Ism")
        database.getDao().insertWeather(task)
        database.getDao().insertWeather(task1)
        database.getDao().delete(task)
        database.getDao().delete(task1)


        //when
        val loaded = database.getDao().getAll().first()

        //Then
        assertThat(loaded.size,`is`(0))


    }


    @Test
    fun getAllFavourits ()= runBlockingTest{

        //Given
        val task = FavTable(0,5.5,4.4,"Ism")
        val task1 = FavTable(1,5.5,4.4,"Ism")
        val task2 = FavTable(2,5.5,4.4,"Ism")
        val task3 = FavTable(3,5.5,4.4,"Ism")
        database.getDao().insertWeather(task)
        database.getDao().insertWeather(task1)
        database.getDao().insertWeather(task2)
        database.getDao().insertWeather(task3)

        //when
        val loaded = database.getDao().getAll().first()

        //Then
        assertThat(loaded.size,`is`(3))


    }

}