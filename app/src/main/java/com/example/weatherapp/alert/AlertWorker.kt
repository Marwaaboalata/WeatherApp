package com.example.weatherapp.alert

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class AlertWorker(var context : Context,params : WorkerParameters) : CoroutineWorker(context,params) {
    override suspend fun doWork(): Result {
        TODO("Not yet implemented")
    }
}
