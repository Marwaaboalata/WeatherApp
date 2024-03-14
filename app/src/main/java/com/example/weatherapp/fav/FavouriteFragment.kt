package com.example.weatherapp.fav
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.main.view.MyFactory
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentFavouriteBinding
import com.example.weatherapp.db.LocalDataSourceImp
import com.example.weatherapp.model.FavTable
import com.example.weatherapp.model.ReposatoryImp
import com.example.weatherapp.network.api.RemoteDataSourceImp
import com.example.weatherapp.utils.ApiState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavouriteFragment : Fragment(), Listener {
    private lateinit var binding: FragmentFavouriteBinding
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var myAdapter: MyFavAdapterList
    lateinit var localviewModel: FavViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addToFavButton.setOnClickListener{
            findNavController().navigate(R.id.action_favouriteFragment_to_mapFragment)
        }

        val myFactory=
            MyFactory(ReposatoryImp(RemoteDataSourceImp(), LocalDataSourceImp(requireContext())))
        localviewModel = ViewModelProvider(this, myFactory).get(FavViewModel::class.java)
        localviewModel.getFavWeather()

        myAdapter = MyFavAdapterList(this)
        linearLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.favRecyc.apply {
            adapter = myAdapter
            layoutManager = linearLayoutManager
        }


        lifecycleScope.launch {
            localviewModel.listData.collectLatest {
                when (it) {
                    is ApiState.Loading ->{
                      //  binding.progressBar.visibility = View.VISIBLE
                                        }
                    is ApiState.Success -> {
//                        binding.progressBar.visibility = View.GONE
//                        productAdapter.submitList(it.data)
                        myAdapter.submitList(it.data)
                    }
//                    is ApiState.Failure -> {
//                        binding.progressBar.visibility = View.GONE
//                        Toast.makeText(this@ProductsActivity, "Failed to load products. Please try again later.", Toast.LENGTH_SHORT).show()
//                    }
                    else -> {}
                }
            }
        }
//        localviewModel.listData.observe(requireActivity()) {
//
//            myAdapter.submitList(it)
//
//        }
    }

    override fun onClickDelete(fav: FavTable) {
        localviewModel.deleteWeather(fav)
    }

    override fun onClickCard(fav: FavTable,view: View) {
        val bundle = bundleOf("weather" to fav)
        Navigation.findNavController(view).navigate(R.id.action_favouriteFragment_to_fragmentFavItemView,bundle)

    }
}
