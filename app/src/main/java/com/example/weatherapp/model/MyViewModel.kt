package com.example.mvvm.main.view


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.ReposatoryInterface
import com.example.weatherapp.utils.ApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.catch


class MyViewModel(var repo : ReposatoryInterface): ViewModel() {

    private val list = MutableStateFlow<ApiState>(ApiState.Loading())

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

//    : List<ListItem?>? {
//        return repo.getAllResponseList()
//    }

  /*  private val list = MutableLiveData<List<ProductsItem>>()


    init {
      //  getRemoteProduct()
        //getFavProducts()
    }

    var listData: LiveData<List<ProductsItem>> = list

//   fun getRemoteData
   fun getFavProducts (){
       try {
           viewModelScope.launch (Dispatchers.IO){
               val res= repo.getFavLocal()
               withContext(Dispatchers.Main){
                   list.postValue(res)

               }
           }

       }
       catch (ex :Exception ){
           Log.i("TAG", "getFavProducts: "+ex.localizedMessage)
       }

   }



    fun deleteProduct (productsItem: ProductsItem){
        viewModelScope.launch (Dispatchers.IO){
            repo.deleteProduct(productsItem)
            getFavProducts()
        }
    }

*/
}