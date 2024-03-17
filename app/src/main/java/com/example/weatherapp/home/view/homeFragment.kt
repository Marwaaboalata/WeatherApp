package com.example.weatherapp.home.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Looper
import android.os.PerformanceHintManager
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.main.view.MyFactory
import com.example.weatherapp.R
import com.example.weatherapp.db.LocalDataSourceImp
import com.example.weatherapp.model.ReposatoryImp
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.network.api.RemoteDataSourceImp
import com.example.weatherapp.utils.ApiState
import com.example.weatherapp.utils.Constant
import com.example.weatherapp.utils.PreferenceManager
import com.example.weatherapp.utils.PreferenceManager.getAppLocationByMap
import com.example.weatherapp.utils.PreferenceManager.setAppLocationForAlert
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale

var isLocationRecive = false
var isNetworkavailable =true
class homeFragment : Fragment() {
     var longtude:Double=0.0
     var latitude:Double=0.0


    lateinit var hourlyRecyclerView: RecyclerView
    lateinit var weaklyRecyclerView: RecyclerView
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var linearLayoutManagerWeak: LinearLayoutManager

    lateinit var statusTv:TextView
    lateinit var tempNumTv:TextView
    lateinit var cityTv:TextView
    lateinit var visibilityTv:TextView
    lateinit var humitidyTv:TextView
    lateinit var presurTv:TextView
    lateinit var windTv:TextView
    lateinit var cloudTv:TextView
    lateinit var ultravilotTv:TextView
    lateinit var progressBar:ProgressBar


    private  val  PERMISSION_ID=5005
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var myHourAdapter = MyHourlyAdapterList()
    var myWeakAdapter = MyWeaklyAdapterList()


    lateinit var viewModel: MyViewModel
    //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isNetworkavailable=isNetworkAvailable(requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)

        linearLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        hourlyRecyclerView.apply {
            adapter = myHourAdapter
            layoutManager = linearLayoutManager
        }

        linearLayoutManagerWeak = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        weaklyRecyclerView.apply {
            adapter = myWeakAdapter
            layoutManager = linearLayoutManagerWeak
        }

        val myFactory = MyFactory(ReposatoryImp(RemoteDataSourceImp(),LocalDataSourceImp(requireContext())))
        viewModel = ViewModelProvider(this, myFactory).get(MyViewModel::class.java)

     /*
        lifecycleScope.launch {
        if (locationForHome.isintiate){
           viewModel.getResponseData("${locationForHome.lat}","${locationForHome.long}","metric","en")}    // Call getResponseData to initiate API call
        }*/
        if (!isNetworkAvailable(requireContext())){

                Toast.makeText(requireContext(), "Network not Available", Toast.LENGTH_SHORT).show()
            Log.i("TAGOO", "onResume:!isNetworkAvailable(requireContext()) ")
                viewModel.getOflineWeather()
                lifecycleScope.launch {
                    viewModel.listData.collectLatest {
                        when (it) {
                            is ApiState.Loading ->{
                                Log.i("TAGOO", "loading: ")

                            }

                            is ApiState.Success -> {
                                progressBar.visibility = View.GONE


                                addDataToUI(it.data.get(0))
                                Log.i("TAGOO", "onResume: "+it.data.get((it.data.size)-1).lat)

                            }
                            is ApiState.Failur->{

                                Log.i("TAGOO", "failur: "+it.msg)
                            }

                            else -> {
                                Log.i("TAGOO", "EROR: ")
                            }
                        }

                }
//
            }
        }
        else {

            lifecycleScope.launch {
                viewModel.listForView.collectLatest { res ->
                    when (res) {

                        is ApiState.Success -> {
                            progressBar.visibility = View.GONE

                            Log.i("TAGO", "onViewCreated:long+lat: " + latitude + "," + longtude)
                            //    myAdapter.submitList(res.list)
                            val weather = res.data
                            //
                            viewModel.insertHome(weather)
                            //
                            addDataToUI(weather)
                            /*
                        tempNumTv.text=weather.current.temp.toInt().toString()+" "+getDegree(requireContext())
                        //  Log.i("TAG", "onViewCreated: "+list)

                        myHourAdapter.submitList(weather.hourly)
                        myWeakAdapter.submitList(weather.daily)
                        humitidyTv.text=weather.current.humidity.toString()
                        presurTv.text=weather.current.pressure.toString()
                        windTv.text=weather.current.wind_deg.toString()
                        visibilityTv.text=weather.current.visibility.toString()
                        cloudTv.text=weather.current.clouds.toString()
                        ultravilotTv.text=weather.current.uvi.toString()
                        */
                        }

                        is ApiState.Loading -> {
                            progressBar.visibility = View.VISIBLE


                            //  Log.i("TAG", "onViewCreated: "+list?.get(0)?.dtTxt)
                            Log.i("TAG", "onViewCreated: Loaded")
                        }

                        is ApiState.Failur -> {
                            Toast.makeText(
                                requireContext(),
                                "Fail to load Data",
                                Toast.LENGTH_SHORT
                            ).show()

                            Log.i("TAG", "ApiState.Failur: errrrooooooooooooooooooooooooooooooooor")

                        }

                        else -> {
                            Log.i("TAG", "onCreate: errrrooooooooooooooooooooooooooooooooor")
                        }

                    }

                }
            }
        }

}



    override fun onResume() {
        super.onResume()
        if (isNetworkAvailable(requireContext())){
        isLocationRecive =false
        if (PreferenceManager.getSelectedLocation(requireContext()) == Constant.LOCATION_MAP) {

            lifecycleScope.launch {
            //  myDegree= PreferenceManager.getSelectedTemperatureUnit(requireContext())
            ///////
                val pair = getAppLocationByMap(requireContext())

                viewModel.getResponseData(
                    pair.first, pair.second,
                    lang = PreferenceManager.getSelectedLanguage(requireContext()),
                    units = PreferenceManager.getSelectedTemperatureUnit(requireContext()),
                    context = requireContext()
                )
                cityTv.text =getCityName((pair.first as String).toDouble(),(pair.second as String).toDouble())
                Log.i("TAGO", "onLocationResult: pair"+pair.first.toString())
               setAppLocationForAlert(requireContext(),pair.first.toString(),pair.second.toString())

            }   } else{
        getLocation()}
        lifecycleScope.launch {
            viewModel.listForView.collectLatest {
                    res->
                when(res){

                    is ApiState.Success->{
                        Log.i("TAGO", "onViewCreated:long+lat: "+latitude+","+longtude)
                        //    myAdapter.submitList(res.list)
                        val weather=res.data
                        addDataToUI(weather)
                        viewModel.insertHome(weather)


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
        }
        else{
            Toast.makeText(requireContext(), "Network not Available", Toast.LENGTH_SHORT).show()

            lifecycleScope.launch {
                viewModel.listData.collectLatest {
                    when (it) {
                        is ApiState.Loading ->{
                            //  binding.progressBar.visibility = View.VISIBLE
                        }
                        is ApiState.Success -> {
                            progressBar.visibility = View.GONE

                            addDataToUI(it.data.get((it.data.size)-1))
                            Log.i("TAGOO", "onResume: "+it.data.get((it.data.size)-1).lat)
                        }

                        else -> {}
                    }
                }
            }
//
        }

    }

    override fun onStop() {
        super.onStop()
        isLocationRecive =false

    }

    override fun onPause() {
        super.onPause()
        isLocationRecive =false
        ///
    //    getLocation()
    }









    private val mLocationCallBack: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: android.location.Location? = locationResult.lastLocation
            if (mLastLocation != null) {
                longtude = mLastLocation.longitude
                latitude = mLastLocation.latitude
                val geocoder = Geocoder(requireContext(), Locale.getDefault())

                val addresses = geocoder.getFromLocation(latitude, longtude, 1)
                if (addresses != null) {
                    if (addresses.isNotEmpty()) {
                        val address = addresses?.get(0)?.countryName.toString()+", "+addresses?.get(0)?.adminArea//+", "+addresses?.get(0)?.locality
//                        val addressLine = address.get(0)//getAddressLine(0)
                        cityTv.text = address

                    }
                }
                if (!isLocationRecive) {
                   isLocationRecive =true
                    lifecycleScope.launch {
                      //  myDegree= PreferenceManager.getSelectedTemperatureUnit(requireContext())
//                       ///////
//                        if (PreferenceManager.getSelectedLocation(requireContext()) == Constant.LOCATION_MAP) {
//                            val pair = getAppLocationByMap(requireContext())
//
//                            viewModel.getResponseData(
//                                pair.first, pair.second,
//                                lang = PreferenceManager.getSelectedLanguage(requireContext()),
//                                units = PreferenceManager.getSelectedTemperatureUnit(requireContext())
//                            )
//                            Log.i("TAGO", "onLocationResult: pair"+pair.first.toString())
//
//                        }    else{
                        viewModel.getResponseData(
                            "${latitude}",
                            "${longtude}",
                            PreferenceManager.getSelectedTemperatureUnit(requireContext()),
                            PreferenceManager.getSelectedLanguage(requireContext()),requireContext()
                        )
                        setAppLocationForAlert(requireContext(),longtude.toString(),latitude.toString())

                        //}



                      //  Locale(getLanguageLocale())

                    }
                }
                Log.i("TAG", "long:from home "+longtude)

                Log.i("TAG", "latttt:from home  "+latitude)
                //

               // val geocoder = Geocoder(this@homeFragment, Locale.getDefault())

            }


        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==PERMISSION_ID){
            if (grantResults[0]== PackageManager.PERMISSION_GRANTED||grantResults[1]== PackageManager.PERMISSION_GRANTED)
            {  getLocation()}
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    private fun checkPermission(): Boolean {
        val result = ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        return result

    }
    private fun isLocationEnabled():Boolean{
     //   val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val locationManager: LocationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager



        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
    private fun getLocation(): Unit {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                requestNewLocationData()

            } else {
                //Toast.makeText(this, "Turn On Location", Toast.LENGTH_LONG)
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }
    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        mLocationRequest.setInterval(0)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(
            mLocationRequest,
            mLocationCallBack,
            Looper.myLooper()

        )
    }



    //intiate

    fun init(view: View){
        statusTv=view.findViewById(R.id.statusTv)
        tempNumTv=view.findViewById(R.id.tempnumTv)
        cityTv=view.findViewById(R.id.cityTv)
        humitidyTv=view.findViewById(R.id.humidity_measure)
        windTv=view.findViewById(R.id.wind_measure)
        visibilityTv=view.findViewById(R.id.visibility_measure)
        presurTv=view.findViewById(R.id.pressure_measure)
        cloudTv=view.findViewById(R.id.cloud_measure)
        ultravilotTv=view.findViewById(R.id.ultraVio_measure)
        hourlyRecyclerView = view.findViewById(R.id.hourRecycV)
        weaklyRecyclerView = view.findViewById(R.id.weakRecycV)
        progressBar = view.findViewById(R.id.progressBar)



    }
    fun getDegree(context: Context):String{
        if (PreferenceManager.getSelectedTemperatureUnit(context)== Constant.Ar_Language){
            return " C°"}
        else if (PreferenceManager.getSelectedTemperatureUnit(context)== Constant.Unit_Fahrenheit){
            return " F°"}
        else if (PreferenceManager.getSelectedTemperatureUnit(context)== Constant.Unit_kelvin){
            return " K°"}
        else return " C°"
    }
    fun getLanguageLocale(): String {
        return AppCompatDelegate.getApplicationLocales().toLanguageTags()
    }

    fun getCityName(longitude: Double, latitude: Double): String {
        try {
            val geocoder = Geocoder(requireContext(), Locale.getDefault())

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
    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }

    fun addDataToUI(weather:WeatherResponse){
        tempNumTv.text=weather.current.temp.toInt().toString()+" "+getDegree(requireContext())
        //  Log.i("TAG", "onViewCreated: "+list)

        myHourAdapter.submitList(weather.hourly)
        myWeakAdapter.submitList(weather.daily)
        humitidyTv.text=weather.current.humidity.toString()
        presurTv.text=weather.current.pressure.toString()
        windTv.text=weather.current.wind_deg.toString()
        visibilityTv.text=weather.current.visibility.toString()
        cloudTv.text=weather.current.clouds.toString()
        ultravilotTv.text=weather.current.uvi.toString()
        statusTv.text=weather.current.weather[0].description
    }
}

