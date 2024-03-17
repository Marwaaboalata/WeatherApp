package com.example.weatherapp.fav

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.model.FavTable
import com.google.android.material.card.MaterialCardView

//var myLamda:(Language)->Unit
class MyFavAdapterList (var activity: Listener): ListAdapter<FavTable, LanguageViewHolder>(LangDiffUtil()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
       // val inflater: Inflater =
          //  parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View =
            layoutInflater.inflate(R.layout.fav_item_layout, parent, false)
        return LanguageViewHolder(view)
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        val currentObj=getItem(position)
        holder.tv.text=currentObj.city.toString()
        holder.imgDelete.setOnClickListener{
            activity.onClickDelete(currentObj)


        }
        holder.favCard.setOnClickListener{
            activity.onClickCard(currentObj,holder.favCard)
        }

    }


}
class LanguageViewHolder(view:View) : RecyclerView.ViewHolder(view){
 var imgDelete :ImageView=view.findViewById(R.id.img_delete)
    var tv :TextView=view.findViewById(R.id.fav_city_name)
    var favCard :MaterialCardView =view.findViewById(R.id.fav_cared)
}



class LangDiffUtil :DiffUtil.ItemCallback<FavTable>(){
    override fun areItemsTheSame(oldItem: FavTable, newItem: FavTable): Boolean {
        return newItem === oldItem
    }

    override fun areContentsTheSame(oldItem: FavTable, newItem: FavTable): Boolean {
        return oldItem == newItem
    }


}

