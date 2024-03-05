package com.example.weatherapp.model

import android.util.Log
import com.example.weatherapp.db.LocalDataSourceInterface
import com.example.weatherapp.network.api.RemoteDataSourceInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ReposatoryImp(var remote :RemoteDataSourceInterface,var local:LocalDataSourceInterface):ReposatoryInterface {

    override suspend fun getAllResponseList( lat: String?,
                                             lon: String?,
                                             units: String?,
                                             lang: String?)= flow {
        emit(remote.getResponse( lat=lat,
            lon=lon,
        units=units,
        lang=lang))
    }.flowOn(Dispatchers.IO)


  /*  : List<ListItem?>? {
        Log.i("TAG", "getAllRemoteProducts: "+ (remote.getResponse()?.list?.get(0)?.dt ?: "0000"))
        return remote.getResponse()?.list

    }*/
}