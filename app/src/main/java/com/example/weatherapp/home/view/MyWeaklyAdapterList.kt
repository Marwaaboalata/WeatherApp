package com.example.weatherapp.home.view

import android.media.audiofx.BassBoost
import android.provider.Settings
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
import com.example.weatherapp.model.Daily
import com.example.weatherapp.model.Hourly
import java.text.SimpleDateFormat
import java.util.Date

class MyWeaklyAdapterList (): ListAdapter<Daily, DailyViewHolder>(DailyDiffUtil()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
       // val inflater: Inflater =
          //  parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View =
            layoutInflater.inflate(R.layout.item_weakly_layout, parent, false)
        return DailyViewHolder(view)
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        val currentObj=getItem(position)
        Glide
            .with(holder.imgDay.getContext())
            .load("https://openweathermap.org/img/wn/"+currentObj.weather.get(0).icon+".png")
            .into(holder.imgDay)
        //holder.img.setImageResource(currentObj.i)
//        Glide.with(holder.img.getContext()).load("https://openweathermap.org/img/wn/"+currentObj.weather.get(0).icon+".png")
//            .into(holder.img)

        //holder.daytv.text= SimpleDateFormat("E").format(Date(currentObj.dt* 1000) ).toString()
      //  holder.hourTemp.text=currentObj.temp.toString()
        holder.daytv.text= getDay( currentObj.dt)
        holder.dayTemp.text=currentObj.temp.min.toString()
        holder.dayDescription.text=currentObj.weather.get(0).description

        Log.i("TAG", "onBindViewHolder: "+currentObj.dt.toString())

        Log.i("TAG", "onBindViewHolder: "+currentObj.temp.toString())



    }


}
class DailyViewHolder(view:View) : RecyclerView.ViewHolder(view){
 //var img :ImageView=view.findViewById(R.id.hour_icon_txt)
    var daytv :TextView=view.findViewById(R.id.tv_day)
    var dayTemp:TextView=view.findViewById(R.id.tv_temp_day)
    var dayDescription:TextView=view.findViewById(R.id.day_description)
    var imgDay:ImageView=view.findViewById(R.id.img_day)
}



class DailyDiffUtil :DiffUtil.ItemCallback<Daily>(){
    override fun areItemsTheSame(oldItem: Daily, newItem: Daily): Boolean {
        return newItem === oldItem
    }

    override fun areContentsTheSame(oldItem: Daily, newItem: Daily): Boolean {
        return oldItem == newItem
    }
}
/*
fun getWeekDay(time: Int):String{
    val day = SimpleDateFormat().format(time * 1000L)
    var fullDayName = ""

   // if(Settings.settings.lang =="English"){
        when (day){
            "Sat" -> fullDayName = "Saturday"
            "Sun" -> fullDayName = "Sunday"
            "Mon" -> fullDayName = "Monday"
            "Tue" -> fullDayName = "Tuesday"
            "Wed" -> fullDayName = "Wednesday"
            "Thu" -> fullDayName = "Thursday"
            "Fri" -> fullDayName = "Friday"
        }


//}else{
//        when (day){
//            "Sat" -> fullDayName = "السبت"
//            "Sun" -> fullDayName = "الاحد"
//            "Mon" -> fullDayName = "الاثنين"
//            "Tue" -> fullDayName = "الثلاثاء"
//            "Wed" -> fullDayName = "الاربعاء"
//            "Thu" -> fullDayName = "الخميس"
//            "Fri" -> fullDayName = "الجمعة"
//        }
//    }
    return fullDayName
} */
fun getDay(dt: Int): String {
    val cityTxtFormat = SimpleDateFormat("E")
    val cityTxtData = Date(dt.toLong() * 1000)
    return cityTxtFormat.format(cityTxtData)
}
