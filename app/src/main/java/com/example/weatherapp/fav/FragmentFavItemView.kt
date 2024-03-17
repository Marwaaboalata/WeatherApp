package com.example.weatherapp.fav

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.main.view.MyFactory
import com.example.weatherapp.home.view.MyViewModel
import com.example.weatherapp.databinding.FragmentFavItemViewBinding
import com.example.weatherapp.db.LocalDataSourceImp
import com.example.weatherapp.home.view.MyHourlyAdapterList
import com.example.weatherapp.home.view.MyWeaklyAdapterList
import com.example.weatherapp.model.FavTable
import com.example.weatherapp.model.ReposatoryImp
import com.example.weatherapp.network.api.RemoteDataSourceImp
import com.example.weatherapp.utils.ApiState
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class FragmentFavItemView : Fragment() {
    var longtude:Double=0.0
    var latitude:Double=0.0


    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var linearLayoutManagerWeak: LinearLayoutManager
    lateinit var binding: FragmentFavItemViewBinding

/*
    lateinit var hourlyRecyclerView: RecyclerView
    lateinit var weaklyRecyclerView: RecyclerView
    lateinit var tempNumTv: TextView
    lateinit var cityTv: TextView
    lateinit var visibilityTv: TextView
    lateinit var humitidyTv: TextView
    lateinit var presurTv: TextView
    lateinit var windTv: TextView
    lateinit var cloudTv: TextView
    lateinit var ultravilotTv: TextView
*/

    private  val  PERMISSION_ID=5005
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var myHourAdapter = MyHourlyAdapterList()
    var myWeakAdapter = MyWeaklyAdapterList()


    lateinit var viewModel: MyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavItemViewBinding.inflate(inflater,container,false)
        return binding.root
        // Inflate the layout for this fragment
     //   return inflater.inflate(R.layout.fragment_fav_item_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val place = arguments?.get("weather") as FavTable
        binding.cityTv.text=place.city



        linearLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.hourRecycV.apply {
            adapter = myHourAdapter
            layoutManager = linearLayoutManager
        }


        linearLayoutManagerWeak = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.weakRecycV.apply {
            adapter = myWeakAdapter
            layoutManager = linearLayoutManagerWeak
        }


        val myFactory = MyFactory(
            ReposatoryImp(
                RemoteDataSourceImp(),
                LocalDataSourceImp(requireContext())
            )
        )
        viewModel = ViewModelProvider(this, myFactory).get(MyViewModel::class.java)


           lifecycleScope.launch {

              viewModel.getResponseData("${place.lat}","${place.lon}","metric","en",requireContext())   // Call getResponseData to initiate API call
           }


        lifecycleScope.launch {
            viewModel.listForView.collectLatest {
                    res->
                when(res){

                    is ApiState.Success->{
                        Log.i("TAGO", "onViewCreated:long+lat: "+latitude+","+longtude)
                        //    myAdapter.submitList(res.list)
                        val weather=res.data
                        binding.tempnumTv.text=weather.current.temp.toInt().toString()
                        //  Log.i("TAG", "onViewCreated: "+list)

                        myHourAdapter.submitList(weather.hourly)
                        myWeakAdapter.submitList(weather.daily)
                        binding.humidityMeasure.text=weather.current.humidity.toString()
                        binding.pressureMeasure.text=weather.current.pressure.toString()
                        binding.windMeasure.text=weather.current.wind_deg.toString()
                        binding.visibilityMeasure.text=weather.current.visibility.toString()
                        binding.cloudMeasure.text=weather.current.clouds.toString()
                        binding.ultraVioMeasure.text=weather.current.uvi.toString()
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


}