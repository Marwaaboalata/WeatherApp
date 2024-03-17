package com.example.weatherapp.home.view


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.ReposatoryInterface
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.utils.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.catch


class MyViewModel(var repo : ReposatoryInterface): ViewModel() {

    private val list = MutableStateFlow<ApiState<WeatherResponse>>(ApiState.Loading())

    val listForView  = list.asStateFlow()

    private val homelist = MutableStateFlow<ApiState<List<WeatherResponse>>>(ApiState.Loading())
    val listData = homelist.asStateFlow()
    suspend fun getResponseData (lat: String?,
                                 lon: String?,
                                 units: String?,
                                 lang: String?,context: Context){
//        if (isNetworkAvailable(context)){
        try {
            viewModelScope.launch{

                repo.getAllResponseList( lat=lat,
                    lon=lon,
                    units=units,
                    lang=lang)
                    .catch {
                            e->
                        list.value = ApiState.Failur(e)

                    }.collect{
                            lamda->
                        list.value= ApiState.Success(lamda)
                        insertHome(lamda)
                    }


            }}
        catch (ex :Exception ){
            Log.i("TAG", "getAllRemoteroducts: "+ex.localizedMessage)
        }
       /* }
            else{
            try {
                viewModelScope.launch (Dispatchers.IO){
                    val res= repo.getHomes()
                        .catch {
                                e->
                            homelist.value = ApiState.Failur(e)

                        }.collect{

                            homelist.value= ApiState.Success(it)
                        }

                }
                Log.i("TAG", "getAllRemoteroducts: offline ")

            }
            catch (ex :Exception ){
                Log.i("TAG", "getFavProducts: "+ex.localizedMessage)
            }
            }

        */

    }


    //////

    fun insertHome (weather: WeatherResponse){
        viewModelScope.launch (Dispatchers.IO){
            repo.insertHome(weather)
            getOflineWeather()
        }
    }
    fun getOflineWeather (){
        try {
            viewModelScope.launch (Dispatchers.IO){
                val res= repo.getHomes()
                    .catch {
                            e->
                        homelist.value = ApiState.Failur(e)

                    }.collect{

                        homelist.value= ApiState.Success(it)
                    }

            }

        }
        catch (ex :Exception ){
            Log.i("TAG", "getFavProducts: "+ex.localizedMessage)
        }

    }
/*
    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }

 */
}