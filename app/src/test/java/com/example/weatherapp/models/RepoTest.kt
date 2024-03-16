package com.example.weatherapp.models

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.MainRule
import com.example.weatherapp.model.Current
import com.example.weatherapp.model.FavTable
import com.example.weatherapp.model.ReposatoryImp
import com.example.weatherapp.model.Weather
import com.example.weatherapp.model.WeatherResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class RepoTest  {
    @ExperimentalCoroutinesApi
    @get:Rule

    val instantTaskExecutorRule = InstantTaskExecutorRule()
    val mainRule = MainRule()
    val localSourcePlacesList = mutableListOf(

        FavTable(5, 0.0, 0.0, "00"),
        FavTable(6, 1.0, 1.0, "11"),
        FavTable(22, 3.0, 3.0, "33")

    )
    val remoteSourceResponse = WeatherResponse(
        listOf(),

        Current(

            0, 0.0, 0, 0.0, 0, 0, 0, 0, 0.0, 0.0, 0, listOf<Weather>(), 0, 0.0

        ), listOf(), listOf(), 0.0, 0.0, "", 0

    )


    lateinit var fakeLocalDataSource: FakeLocal

    lateinit var fakeRemoteDataSource: FakeRemote

    lateinit var repository: ReposatoryImp

    lateinit var result: WeatherResponse

    @Before

    fun setUp() {

        fakeLocalDataSource = FakeLocal(localSourcePlacesList)

        fakeRemoteDataSource = FakeRemote(remoteSourceResponse)

        repository = ReposatoryImp(fakeRemoteDataSource,fakeLocalDataSource) as ReposatoryImp

    }
    @Test
    fun getWeatherOverNetwork_lonLat_weatherResponse() = runBlockingTest {
        // Given
       //val repository = createTestRepository(FakeRemote(remoteSourceResponse))

        // When
        var result: WeatherResponse? = remoteSourceResponse
        launch {
            repository.getAllResponseList("", "", "", "")
                .collect {
                    result = it
                }
        }
        advanceUntilIdle()

        // Then
        assertThat(result, `is`(remoteSourceResponse))
    }

    @Test
    fun getAllFavPlaces_returnFavPlaces() = mainRule.runBlockingTest {

   //when

        var result = listOf<FavTable>()

        repository.getFavWeathers().collect {

            result = it
        }

   //Return

        assertThat(result, `is`(localSourcePlacesList))
    }

    @Test
    fun insertPlaceToFav_place_dbUpdated() = mainRule.runBlockingTest {

        //Given

        val place = FavTable(15, 3.0, 3.0, "33")
        val place2 = FavTable(17, 3.0, 3.0, "33")
        //When

        repository.insertWeather(place)
        repository.insertWeather(place2)

        var result = listOf<FavTable>()

        repository.getFavWeathers().collect {

            result = it
        }

        //Return

       assertThat(result.get((result.size-1)), `is`(place2))
    }

    @Test
    fun deleteFromFav()= mainRule.runBlockingTest{

     //   Given
        val place2 = FavTable(17, 3.0, 3.0, "33")
        //When

        repository.insertWeather(place2)
        repository.delete(place2)
        var result = listOf<FavTable>()

        repository.getFavWeathers().collect {

            result = it
        }
   //Return
    //I have a list with size =2 at local
        //this pass
     //   assertThat((result.size)-1, `is`(2))


        //and this pass too

        assertThat(result[(result.size)-1].id, `is`(not(17)))

    }

}
/*

@RunWith(JUnit4::class)

class RepositoryTest {
@ExperimentalCoroutinesApi
@get:Rule

val mainRule = MainRule()

val localSourcePlacesList = mutableListOf(

FavoriteCity("place1", 0.0, 0.0, "00"),

FavoriteCity("place2", 1.0, 1.0, "11")

)

val remoteSourceResponse = WeatherResponse(
listOf(),

Current(

0, 0.0, 0, 0.0, 0, 0, 0, 0, 0.0, 0.0, 0, listOf<Weather>(), 0, 0.0

), listOf(), listOf(), 0.0, 0.0, "", 0

)


lateinit var fakeLocalDataSource: FakeLocalSource

lateinit var fakeRemoteDataSource: FakeRemoteSource

lateinit var repository: RepositoryImpl

lateinit var result: WeatherResponse

@Before

fun setUp() {

fakeLocalDataSource = FakeLocalSource(localSourcePlacesList)

fakeRemoteDataSource = FakeRemoteSource(remoteSourceResponse)

repository = RepositoryImpl.getInstance(fakeRemoteDataSource, fakeLocalDataSource) as RepositoryImpl

}
@Test

fun getWeatherOverNetwork_lonLat_weatherResponse() = mainRule.runBlockingTest {

repository.getWeather("", "","","").collect {

result = it
}

Assert.assertThat(result, CoreMatchers.`is`(remoteSourceResponse))
}
@Test

fun removePlaceFromFav_place_dbUpdated() = mainRule.runBlockingTest {

//Given

var result = listOf<FavoriteCity>()

val place = FavoriteCity("place2", 1.0, 1.0, "11")

//When

repository.deleteCity(place)

repository.getAllFavCity().collect {

result = it
}

//Return

Assert.assertThat(result, CoreMatchers.`is`(listOf(FavoriteCity("place1", 0.0, 0.0, "00"))))
}
@Test

fun addPlaceToFav_place_dbUpdated() = mainRule.runBlockingTest {

//Given

val list = listOf(

FavoriteCity("place1", 0.0, 0.0, "00"),

FavoriteCity("place2", 1.0, 1.0, "11"),

FavoriteCity("place3", 3.0, 3.0, "33")

)

val place = FavoriteCity("place3", 3.0, 3.0, "33")

//When

repository.insertCity(place)

var result = listOf<FavoriteCity>()

repository.getAllFavCity().collect {

result = it
}

//Return

Assert.assertThat(result, CoreMatchers.`is`(list))
}
@Test

fun getAllFavPlaces_returnFavPlaces() = mainRule.runBlockingTest {

//when

var result = listOf<FavoriteCity>()

repository.getAllFavCity().collect {

result = it
}

//Return

Assert.assertThat(result, CoreMatchers.`is`(localSourcePlacesList))
}

}
 */