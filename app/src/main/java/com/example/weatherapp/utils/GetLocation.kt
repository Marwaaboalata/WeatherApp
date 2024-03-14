package com.example.weatherapp.utils


/*
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.weatherapp.drawer.isLocationRecive
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.properties.Delegates

class GetLocation(var context: Context, var activity: Activity) {
     var lat:Double=0.0
    var long:Double=0.0
    var isintiate=false


    lateinit var city: String

    private val PERMISSION_ID = 5005
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: android.location.Location? = locationResult.lastLocation
            if (mLastLocation != null) {
                long = mLastLocation.longitude
                lat = mLastLocation.latitude
                val geocoder = Geocoder(context, Locale.getDefault())

                val addresses = geocoder.getFromLocation(lat, long, 1)
                if (addresses != null && addresses.isNotEmpty()) {
                    val address =
                        addresses[0].countryName + ", " + addresses[0].adminArea + ", " + addresses[0].locality
                    city = address
                    isintiate=true
                }
                if (!isLocationRecive) {
                    isLocationRecive = true
                    Log.i("TAG", "long: from class $long")
                    Log.i("TAG", "lat: from class $lat")
                }
            }
        }
    }

    init {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
        getLocation()
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    private fun checkPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    fun getLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                requestNewLocationData()
            } else {
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                activity.startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        fusedLocationProviderClient.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.myLooper()
        )
    }



}
*/

