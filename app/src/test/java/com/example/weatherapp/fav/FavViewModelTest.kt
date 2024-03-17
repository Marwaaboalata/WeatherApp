package com.example.weatherapp.fav

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test


import androidx.annotation.VisibleForTesting
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weatherapp.MainRule
import com.example.weatherapp.model.Current
import com.example.weatherapp.model.FavTable
import com.example.weatherapp.model.ReposatoryInterface
import com.example.weatherapp.model.Weather
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.models.FakeRemote
import com.example.weatherapp.models.FakeRepo
import com.example.weatherapp.utils.ApiState

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.empty
import org.hamcrest.Matchers.hasSize
import org.junit.Before
import org.junit.Rule


import org.junit.runner.RunWith


/*
//@RunWith(JUnit4::class)
//@ExperimentalCoroutinesApi
class FavViewModelTest{

    private lateinit var favViewModel: FavViewModel
    private lateinit var repo: FakeRepo
    /*
        @get:Rule
        val mainCoroutineRule = MainRule()

        @get:Rule
        val instantTaskExecutorRule = InstantTaskExecutorRule()
    */
    @Before
    fun setUp(){
        repo = FakeRepo()
        favViewModel = FavViewModel(repo)


    }

    @Test
    fun getFavWeathers_noParams_WeatherList(){
        //Given

        //When
        val favList =favViewModel.getFavWeather()


        //then
        assertThat(favList,`is`(NotNull()))

    }
/*
    @Test
    fun removePlace_place_dbUpdated ()= mainCoroutineRule.runBlockingTest{
        //Given
        repo.insertWeather(FavTable(1,0.0,0.0,"00"))
        repo.insertWeather(FavTable(2,1.0,1.0,"11"))

        val favList= listOf(FavTable(3,0.0,0.0,"00"))

        //When
        val place = FavTable(4,1.0,1.0,"11")
        favViewModel.deleteWeather(place)

        val result =favViewModel.getFavWeather().getOrAwaitValue{ }
        //Return
        Assert.assertThat(result, CoreMatchers.notNullValue())
        Assert.assertThat(result, `is`(favList))

    }
*/
/*
    @Test
    fun getFavWeather_insertWeather_returnWeatherName()= runBlockingTest {
        favViewModel.insertWeather(FavTable(1,0.0,0.0,"00"))
        favViewModel.getFavWeather()
        var data:List<FavTable> = emptyList()
        val result = favViewModel.listData.first()
        when (result) {
            is ApiState.Success -> {
                data = result.data
            }
            else -> {}
        }
     //Then
        MatcherAssert.assertThat(data.get(0).id, CoreMatchers.`is`(1))
    }

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {

        override fun onChanged(value: T) {
            data = value
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    this.observeForever(observer)

    try {
        afterObserve.invoke()

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }

    } finally {
        this.removeObserver(observer)
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}*/
}


 */


//////////////////
/*
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MyTest{
    @get:Rule
    private lateinit var favViewModel: FavViewModel
    private lateinit var repo: ReposatoryInterface
    val mainCoroutineRule = MainRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Before
    fun setUp(){
        repo = FakeRepo()
        favViewModel = FavViewModel(repo)


    }
    @Test
    fun mytest()=runBlockingTest {
        //Given
        favViewModel.insertWeather(FavTable(0,5.5,4.4,"Ism"))
        favViewModel.getFavWeather()

        lateinit var dataList:List<FavTable>


        //When
        val favList = favViewModel.listData.first()
        when (favList) {
            is ApiState.Success -> {
                dataList = favList.data
            }
            else -> {}
        }


        //then
       assertThat(dataList.get(0).id,`is`(0))
        //assertEquals(1, dataList.size)
    }
    @Test
    fun mytestInsert()=runBlockingTest {
        //Given
        favViewModel.insertWeather(FavTable(0,5.5,4.4,"Ism"))
        favViewModel.getFavWeather()

        lateinit var dataList:List<FavTable>


        //When
        val favList = favViewModel.listData.first()
        when (favList) {
            is ApiState.Success -> {
                dataList = favList.data
            }
            else -> {}
        }


        //then
        assertThat(dataList.get(0).id,`is`(0))
        //assertEquals(1, dataList.size)
    }


}*/
/*
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MyTest {

    @get:Rule
    var mainCoroutineRule = MainRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var favViewModel: FavViewModel
    private lateinit var repo: ReposatoryInterface
   // private lateinit var fakeRemote: FakeRemote



    @Before
    fun setUp() {
        repo = FakeRepo()
        favViewModel = FavViewModel(repo)
    }

    @Test
    fun getAllFavWeathers()= runBlockingTest{
        //Given
        var newList:List<FavTable> = emptyList()
       // when
        favViewModel.getFavWeather()
        val res=favViewModel.listData.first()
        when (res) {
            is ApiState.Success -> {
                newList = res.data
            }
            else -> {}
        }
        //then
        assertThat(newList.size,`is`(3))

    }



/*
    @Test
    fun testGetFavWeathers() = mainCoroutineRule.runBlockingTest {
        // Given
//        val testData = FavTable(1, 5.5, 4.4, "Ism")
//        favViewModel.insertWeather(testData)

        // When
        favViewModel.getFavWeather()

        // Then
        var dataList:List<FavTable> = emptyList()

        // Then
        val favList = favViewModel.listData.first()
        when (favList) {
            is ApiState.Success -> {
                dataList = favList.data
            }
            else -> {}
        }

        assertThat(dataList.get(0).id, `is`(0))
    }
*/
@Test
fun testGetFavWeathers() = mainCoroutineRule.runBlockingTest {
    // Given
    val testData = FavTable(0, 5.5, 4.4, "Ism")
    favViewModel.insertWeather(testData)

    // When
    favViewModel.getFavWeather()

    // Then
    var dataList: List<FavTable>? = null

    // Wait for the coroutine to complete
    advanceUntilIdle()

    // Then
    val favList = favViewModel.listData.first()
    when (favList) {
        is ApiState.Success -> {
            dataList = favList.data
        }
        else -> {}
    }

    assertThat(dataList?.get(0)?.id, `is`(0))
}


    /*
        @Test
        fun testInsert() = runBlockingTest {
            // Given
            val testData = FavTable(0, 5.5, 4.4, "Ism")
            val testData2 = FavTable(1, 5.5, 4.4, "Ism")


            // When
            favViewModel.insertWeather(testData)
            favViewModel.insertWeather(testData2)
            favViewModel.getFavWeather()
            var dataList:List<FavTable> = emptyList()

            // Then
            val favList = favViewModel.listData.first()
            when (favList) {
                is ApiState.Success -> {
                    dataList = favList.data
                }
                else -> {}
            }


           // assertThat(dataList, hasSize(1))
            assertThat(dataList.size, `is`(2))
        }

     */


}


 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class FavViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var favViewModel: FavViewModel
    private lateinit var repo: ReposatoryInterface

    @Before
    fun setUp() {
        repo = FakeRepo()
        favViewModel = FavViewModel(repo)
    }

    @Test
    fun getAllFavWeathers() = runBlockingTest {
        // When
        favViewModel.getFavWeather()

        // when
        var dataList: List<FavTable>? = null

        // Wait for the coroutine to complete
        advanceUntilIdle()

        // Then
        val favList = favViewModel.listData.first()
        when (favList) {
            is ApiState.Success -> {
                dataList = favList.data
            }
            else -> {}
        }

        assertThat(dataList?.size, `is`(3))
    }

    @Test
    fun testInsertAndGetFavWeathers() = mainCoroutineRule.runBlockingTest {
        // Given
        val testData = FavTable(0, 5.5, 4.4, "Ism")

        // When
        favViewModel.insertWeather(testData)
        favViewModel.getFavWeather()

        // Then
        var dataList: List<FavTable>? = null

        // Wait for the coroutine to complete
        advanceUntilIdle()

        // Then
        val favList = favViewModel.listData.first()
        when (favList) {
            is ApiState.Success -> {
                dataList = favList.data
            }
            else -> {}
        }

        assertThat(dataList?.size, `is`(4))
        assertThat(dataList?.get(0)?.id, `is`(0))
    }

    ///////
    @Test
    fun testDeleteWeathers() = mainCoroutineRule.runBlockingTest {
        // Given
        val testData = FavTable(0, 5.5, 4.4, "Ism")

        // When
        favViewModel.insertWeather(testData)
        favViewModel.deleteWeather(testData)
        favViewModel.getFavWeather()

        // Then
        var dataList: List<FavTable> = emptyList()

        // Wait
        advanceUntilIdle()

        // Then
        val favList = favViewModel.listData.first()
        when (favList) {
            is ApiState.Success -> {
                dataList = favList.data
            }
            else -> {}
        }


        assertThat(dataList?.size, `is`(3))
        //assertThat(dataList?.get(0)?.id, `is`(0))
    }


}

