package com.binar.finalproject.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.finalproject.databinding.ItemSearchDestinationBinding
import com.binar.finalproject.model.Destination
import com.binar.finalproject.model.airport.Airport

class SearchDestinationAdapter(private var listDestination : List<Airport>) : RecyclerView.Adapter<SearchDestinationAdapter.ViewHolder>() {

    var onClickDestination : ((Airport)-> Unit)? = null
    class ViewHolder(var binding : ItemSearchDestinationBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = ItemSearchDestinationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
       return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listDestination.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val destination =  "${listDestination[position].airportLocation} (${listDestination[position].airportCode})"
       holder.binding.tvDestination.text = destination

       holder.binding.itemList.setOnClickListener {
           onClickDestination?.invoke(listDestination[position])
       }
    }

    fun setListSearchDestination(list : List<Airport>){
        listDestination = list
        notifyDataSetChanged()
    }

}