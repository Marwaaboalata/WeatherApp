package com.example.weatherapp.fav

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weatherapp.MainRule
import com.example.weatherapp.home.view.MyViewModel
import com.example.weatherapp.model.FavTable
import com.example.weatherapp.model.ReposatoryInterface
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.models.FakeRepo
import com.example.weatherapp.utils.ApiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
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
        homeViewModel.getResponseData("","","","")

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


}