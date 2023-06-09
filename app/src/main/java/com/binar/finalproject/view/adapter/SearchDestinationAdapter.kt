package com.binar.finalproject.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.finalproject.databinding.ItemSearchDestinationBinding
import com.binar.finalproject.model.Destination

class SearchDestinationAdapter(private var listDestination : List<Destination>) : RecyclerView.Adapter<SearchDestinationAdapter.ViewHolder>() {

    var onClickDestination : ((Destination)-> Unit)? = null
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
       val destination =  "${listDestination[position].airport} (${listDestination[position].code})"
       holder.binding.tvDestination.text = destination

       holder.binding.itemList.setOnClickListener {
           onClickDestination?.invoke(listDestination[position])
       }
    }

    fun setListSearchDestination(list : List<Destination>){
        listDestination = list
        notifyDataSetChanged()
    }

}