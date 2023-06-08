package com.binar.finalproject.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.finalproject.databinding.ItemDestinationFavoriteBinding
import com.binar.finalproject.model.DestinationFavorite

class DestinationFavoriteAdapter(private var listDestination : List<DestinationFavorite>) : RecyclerView.Adapter<DestinationFavoriteAdapter.ViewHolder>(){

    //add data binding later
    class ViewHolder(var binding : ItemDestinationFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bindItem(item : DestinationFavorite){
//
//        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DestinationFavoriteAdapter.ViewHolder {
        val view = ItemDestinationFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: DestinationFavoriteAdapter.ViewHolder, position: Int) {
       holder.binding.tvDestination.text = listDestination[position].flightDestination
       holder.binding.tvMaskapai.text = listDestination[position].airline
       holder.binding.tvDate.text = listDestination[position].date
       holder.binding.tvPrice.text = listDestination[position].price
    }

    override fun getItemCount(): Int {
        return listDestination.size
    }
}