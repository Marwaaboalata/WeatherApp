package com.example.weatherapp.home.view


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.ReposatoryInterface
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.utils.ApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.catch


class MyViewModel(var repo : ReposatoryInterface): ViewModel() {

    private val list = MutableStateFlow<ApiState<WeatherResponse>>(ApiState.Loading())

    val listForView  = list.asStateFlow()
    suspend fun getResponseData (lat: String?,
                                 lon: String?,
                                 units: String?,
                                 lang: String?){
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
                    }

            }}
        catch (ex :Exception ){
            Log.i("TAG", "getAllRemoteroducts: "+ex.localizedMessage)
        }


    }

}