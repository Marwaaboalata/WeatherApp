package com.example.weatherapp.fav

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weatherapp.MainRule
import com.example.weatherapp.home.view.MyViewModel
import com.example.weatherapp.model.Current
import com.example.weatherapp.model.FavTable
import com.example.weatherapp.model.ReposatoryInterface
import com.example.weatherapp.model.Weather
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.models.FakeRepo
import com.example.weatherapp.utils.ApiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class HomeViewModelTest {
    @get:Rule
    var mainCoroutineRule = MainRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var homeViewModel: MyViewModel
    private lateinit var repo: ReposatoryInterface

    @Before
    fun setUp() {
        repo = FakeRepo()
        homeViewModel = MyViewModel(repo)
    }

    @Test
    fun getApiWeathers() = runBlockingTest {
        // When
        homeViewModel.getResponseData("","","","", ApplicationProvider.getApplicationContext())

        // Then
        var dataList: WeatherResponse? = null

        // Wait for the coroutine to complete
        advanceUntilIdle()

        // Then
        val favList = homeViewModel.listForView.first()
        when (favList) {
            is ApiState.Success -> {
                dataList = favList.data
            }
            else -> {}
        }

        MatcherAssert.assertThat(dataList?.lat, CoreMatchers.`is`(0.0))
    }
    @Test
    fun insertHome() = runBlockingTest {
        //Given
        val remoteSourceResponse = WeatherResponse(
            listOf(),

            Current(

                0, 0.0, 0, 0.0, 0, 0, 0, 0, 0.0, 0.0, 0, listOf<Weather>(), 0, 0.0

            ), listOf(), listOf(), 0.0, 0.0, "", 0

        )
        var newRes :List<WeatherResponse> = emptyList()

        //when
        homeViewModel.insertHome(remoteSourceResponse)
        homeViewModel.getOflineWeather()
        // Wait
        advanceUntilIdle()
        //Then
        val result = homeViewModel.listData.first()
      when (result) {
            is ApiState.Success -> {
                newRes = result.data
            }
            else -> {}
        }



        MatcherAssert.assertThat(newRes.isEmpty(), `is`(false))
     //   assertThat(newRes.hasvalue(), `is`(false))


    }


    @Test
    fun getAllHomes() = runBlockingTest {
        //Given
        val remoteSourceResponse2 = WeatherResponse(
            listOf(),

            Current(

                0, 0.0, 0, 0.0, 0, 0, 0, 0, 0.0, 0.0, 0, listOf<Weather>(), 0, 0.0

            ), listOf(), listOf(), 1.0, 1.0, "", 0

        )
        //when
        homeViewModel.insertHome(remoteSourceResponse2)
        homeViewModel.getOflineWeather()
        var dataList: List<WeatherResponse>? = emptyList()



        // Wait for the coroutine to complete
        advanceUntilIdle()

        // Then
        val favList = homeViewModel.listData.first()
        when (favList) {
            is ApiState.Success -> {
                dataList = favList.data
            }
            else -> {}
        }

        // assertThat(result.size,`is`(1))
        MatcherAssert.assertThat(dataList?.isEmpty(), CoreMatchers.`is`(false))

    }






}