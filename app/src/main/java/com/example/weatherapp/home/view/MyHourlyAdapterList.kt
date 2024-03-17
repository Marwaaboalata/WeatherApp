package com.example.weatherapp.home.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.model.Hourly
import java.text.SimpleDateFormat


class MyHourlyAdapterList (): ListAdapter<Hourly, LanguageViewHolder>(LangDiffUtil()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
       // val inflater: Inflater =
          //  parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View =
            layoutInflater.inflate(R.layout.item_hour_layout, parent, false)
        return LanguageViewHolder(view)
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        val currentObj=getItem(position)
        //holder.img.setImageResource(currentObj.i)
        Glide.with(holder.img.getContext()).load("https://openweathermap.org/img/wn/"+currentObj.weather.get(0).icon+".png")
            .into(holder.img)

        holder.hourtv.text= SimpleDateFormat().format(currentObj.dt * 1000L).toString()
        holder.hourTemp.text=currentObj.temp.toString()
        Log.i("TAG", "onBindViewHolder: "+currentObj.dt.toString())

        Log.i("TAG", "onBindViewHolder: "+currentObj.temp.toString())



    }


}
class LanguageViewHolder(view:View) : RecyclerView.ViewHolder(view){
 var img :ImageView=view.findViewById(R.id.hour_icon_txt)
    var hourtv :TextView=view.findViewById(R.id.hour_tv)
    var hourTemp:TextView=view.findViewById(R.id.hour_temp_tv)
}



class LangDiffUtil :DiffUtil.ItemCallback<Hourly>(){
    override fun areItemsTheSame(oldItem: Hourly, newItem: Hourly): Boolean {
        return newItem === oldItem
    }

    override fun areContentsTheSame(oldItem: Hourly, newItem: Hourly): Boolean {
        return oldItem == newItem
    }
}

