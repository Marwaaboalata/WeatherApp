package com.example.weatherapp.map

import android.app.AlertDialog
import android.content.DialogInterface
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mvvm.main.view.MyFactory
import com.example.weatherapp.home.view.MyViewModel
import com.example.weatherapp.R
import com.example.weatherapp.db.LocalDataSourceImp
import com.example.weatherapp.model.FavTable
import com.example.weatherapp.fav.FavViewModel
import com.example.weatherapp.model.ReposatoryImp
import com.example.weatherapp.network.api.RemoteDataSourceImp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Locale

class MapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var saveButton: FloatingActionButton
    private var markerPosition: LatLng? = null
    var cityName:String?=null
    lateinit var  geocoder : Geocoder


    lateinit var localviewModel: FavViewModel
    lateinit var remoteviewModel: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myFactory=MyFactory(ReposatoryImp(RemoteDataSourceImp(),LocalDataSourceImp(requireContext())))
        localviewModel = ViewModelProvider(this, myFactory).get(FavViewModel::class.java)
        remoteviewModel = ViewModelProvider(this, myFactory).get(MyViewModel::class.java)


        mapView = view.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)
        saveButton = view.findViewById(R.id.saveButton)




        saveButton.setOnClickListener {

            if (markerPosition != null) {
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.apply {

                val latitude = markerPosition!!.latitude
                val longitude = markerPosition!!.longitude
                geocoder = Geocoder(requireContext(), Locale.getDefault())

                cityName=getCityName(longitude,latitude)


                /*        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                      if (addresses != null) {
                          if (addresses.isNotEmpty()) {
                              val address = addresses?.get(0)?.countryName.toString()+", "+addresses?.get(0)?.adminArea//+", "+addresses?.get(0)?.locality
      //                        val addressLine = address.get(0)//getAddressLine(0)
                               cityName = address.toString()

                          }}*/
                 //    setIcon(R.drawable.add_location)
                        setTitle("Add City")
                        setMessage("Are you sure you want to add ${cityName} to favorite?")
                        setPositiveButton(getString(R.string.yes)) { _: DialogInterface?, _: Int ->
                            localviewModel.insertWeather(FavTable(lat=longitude, lon = longitude,
                                city=cityName?:"Default1"

                            ))
                            view?.let { it1 ->
                                findNavController().navigate(R.id.action_mapFragment_to_favouriteFragment)
                            }
                        }
                        setNegativeButton(getString(R.string.cancel)) { _, _ ->
                        }.create().show()
                    }





////
/*
                       lifecycleScope.launch {

                          remoteviewModel.getResponseData("${latitude}","${longitude}","metric","en")// Call getResponseData to initiate API call
                       }

                lifecycleScope.launch {
                    remoteviewModel.listForView.collectLatest {
                            res->
                        when(res){

                            is ApiState.Success->{
                               // Log.i("TAGO", "onViewCreated:long+lat: "+latitude+","+longtude)
                                //    myAdapter.submitList(res.list)
                                val weather=res.list
                                localviewModel.insertWeather(weather)

                            }

                            is ApiState.Loading->{


                                //  Log.i("TAG", "onViewCreated: "+list?.get(0)?.dtTxt)
                                Log.i("TAG", "onViewCreated: Loaded")
                            }
                            is ApiState.Failur->{
                                Log.i("TAG", "ApiState.Failur: errrrooooooooooooooooooooooooooooooooor")

                            }

                            else -> {
                                Log.i("TAG", "onCreate: errrrooooooooooooooooooooooooooooooooor")
                            }

                        }

                    }
                }

 */
/////


           //     Log.d("MapFragment", "Latitude: $latitude, Longitude: $longitude")
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.setOnMapClickListener { latLng ->
            // Update marker position
            markerPosition = latLng
            updateMarkerPosition(latLng)
        }
    }

    private fun updateMarkerPosition(latLng: LatLng) {
        val marker = MarkerOptions()
        marker.position(latLng)
        googleMap.clear()
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
        googleMap.addMarker(marker)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    fun getCityName(longitude: Double, latitude: Double): String {
        try {
            val theAddress = geocoder.getFromLocation(latitude, longitude, 5)
            if (theAddress!!.isNotEmpty()) {
                return theAddress[0]?.adminArea ?: ""
            } else {
                return ""
            }
        } catch (e: Exception) {
            Log.e("MapFragmentError", "Error getting city name: ${e.message}")
            return ""
        }
    }
}


/*

  override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.setOnMapClickListener { latLng ->
            // Update marker position
            markerPosition = latLng
            updateMarkerPosition(latLng)
        }
    }

    private fun updateMarkerPosition(latLng: LatLng) {
        val marker = MarkerOptions()
        marker.position(latLng)
        googleMap.clear()
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
        googleMap.addMarker(marker)
    }
 */





/*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var saveButton: FloatingActionButton
    private var marker: MarkerOptions? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView = view.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)

        saveButton = view.findViewById(R.id.saveButton)
        saveButton.setOnClickListener {
            marker?.position?.let { position ->
                // Save latitude and longitude
                val latitude = position.latitude
                val longitude = position.longitude
                // You can save latitude and longitude here or pass it to another function
                // Example: saveLocation(latitude, longitude)
            }
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            googleMap = it
            val initialLocation = LatLng(0.0, 0.0)
            marker = MarkerOptions().position(initialLocation).title("Marker").draggable(true)
            googleMap.addMarker(marker)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 12.0f))

            googleMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
                override fun onMarkerDragStart(p0: com.google.android.gms.maps.model.Marker) {}
                override fun onMarkerDrag(p0: com.google.android.gms.maps.model.Marker) {}
                override fun onMarkerDragEnd(marker: com.google.android.gms.maps.model.Marker) {
                    // Update marker position
                    marker.position?.let { position ->
                        marker.position = position
                    }
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}
*/
