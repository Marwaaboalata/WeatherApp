package com.example.weatherapp.fav


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.FavTable
import com.example.weatherapp.model.ReposatoryInterface
import com.example.weatherapp.utils.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavViewModel(var repo : ReposatoryInterface): ViewModel() {

  //  private val list = MutableStateFlow<List<FavTable>>()
   // var listData: StateFlow<List<FavTable>> = list
  //private val list = MutableStateFlow<ApiState<FavTable>>(ApiState.Loading())

    private val list = MutableStateFlow<ApiState<List<FavTable>>>(ApiState.Loading())
    val listData = list.asStateFlow()


    fun getFavWeather (){
        try {
            viewModelScope.launch (Dispatchers.IO){
                val res= repo.getFavWeathers()
                    .catch {
                            e->
                        list.value = ApiState.Failur(e)

                    }.collect{

                        list.value= ApiState.Success(it)
                    }

//                    .collect{
//                        list.postValue(it)
//
//                    }

            }

        }
        catch (ex :Exception ){
            Log.i("TAG", "getFavProducts: "+ex.localizedMessage)
        }

    }



    fun deleteWeather (weather: FavTable){
        viewModelScope.launch (Dispatchers.IO){
            repo.delete(weather)
            getFavWeather()
        }
    }
    fun insertWeather (weather: FavTable){
        viewModelScope.launch (Dispatchers.IO){
            repo.insertWeather(weather)
        }
    }

}