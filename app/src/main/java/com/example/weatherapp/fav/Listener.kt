package com.example.weatherapp.fav

import android.view.View
import com.example.weatherapp.model.FavTable

interface Listener {
    fun onClickDelete (fav: FavTable)
    fun onClickCard(fav: FavTable,view: View)
}