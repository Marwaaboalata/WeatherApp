package com.example.weatherapp.alert



import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.AlertTable
import com.example.weatherapp.model.FavTable
import com.example.weatherapp.model.ReposatoryInterface
import com.example.weatherapp.utils.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AlertViewModel(var repo : ReposatoryInterface): ViewModel() {


    private val list = MutableStateFlow<ApiState<List<AlertTable>>>(ApiState.Loading())
    val listData = list.asStateFlow()


    fun getAlerts (){
        try {
            viewModelScope.launch (Dispatchers.IO){
                val res= repo.getAlerts()
                    .catch {
                            e->
                        list.value = ApiState.Failur(e)

                    }.collect{

                        list.value= ApiState.Success(it)
                    }
            }

        }
        catch (ex :Exception ){
            Log.i("TAG", "getFavProducts: "+ex.localizedMessage)
        }

    }



    fun deleteAlert (alert: AlertTable){
        viewModelScope.launch (Dispatchers.IO){
            repo.deleteAlert(alert)
            getAlerts()
        }
    }
    fun insertAlert (alert: AlertTable){
        viewModelScope.launch (Dispatchers.IO){
            repo.insertAlert(alert)
        }
    }

}