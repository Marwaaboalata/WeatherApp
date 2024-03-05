package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.main.view.MyFactory
import com.example.mvvm.main.view.MyViewModel
import com.example.weatherapp.db.LocalDataSourceImp
import com.example.weatherapp.db.LocalDataSourceInterface
import com.example.weatherapp.model.ReposatoryImp
import com.example.weatherapp.model.ReposatoryInterface
import com.example.weatherapp.network.api.RemoteDataSourceImp
import com.example.weatherapp.utils.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class homeFragment : Fragment() {
 lateinit var repo:ReposatoryInterface
    lateinit var recyclerView: RecyclerView
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var tempNumTv:TextView
    lateinit var cityTv:TextView

   // lateinit var myAdapter: MyAdapterList
//    lateinit var list:List<ProductsItem>

    lateinit var viewModel: MyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
       /*repo=ReposatoryImp(RemoteDataSourceImp(),LocalDataSourceImp())


        lifecycleScope.launch(Dispatchers.IO) {
            Log.i("TAG", "onViewCreated: "+ (repo.getAllResponseList().list.get(0)))}
    }*/



//


       // viewModel.listForView

//        recyclerView = findViewById(R.id.productRecyc)
//        myAdapter = MyAdapterList(this)
//        linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
//        recyclerView.apply {
//            adapter = myAdapter
//            layoutManager = linearLayoutManager
//        }



        val myFactory = MyFactory(ReposatoryImp(RemoteDataSourceImp(),LocalDataSourceImp()))
        viewModel = ViewModelProvider(this, myFactory).get(MyViewModel::class.java)
        lifecycleScope.launch {
            viewModel.getResponseData("33.44","-94.04","metric","en") // Call getResponseData to initiate API call
        }


      lifecycleScope.launch {
            viewModel.listForView.collectLatest {
                    res->
                when(res){

                    is ApiState.Success->{
                        //    myAdapter.submitList(res.list)
                        val list=res.list
                       // cityTv.text=list.current.
                        Log.i("TAG", "onViewCreated: "+list)
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