package com.example.mvvm.main.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.alert.AlertViewModel
import com.example.weatherapp.fav.FavViewModel
import com.example.weatherapp.home.view.MyViewModel

import com.example.weatherapp.model.ReposatoryInterface


class MyFactory(private val _repo: ReposatoryInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
            MyViewModel(_repo) as T
        }else if (modelClass.isAssignableFrom(FavViewModel::class.java)){
            FavViewModel(_repo) as T
        }else if (modelClass.isAssignableFrom(AlertViewModel::class.java)){
            AlertViewModel(_repo) as T
        }

        else {
            throw IllegalArgumentException("ViewModel Class not found")
        }
    }
}
