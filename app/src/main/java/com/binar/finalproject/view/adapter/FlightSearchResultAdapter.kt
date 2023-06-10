package com.binar.finalproject.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.finalproject.databinding.ItemDataFlightBinding
import com.binar.finalproject.model.Flight

class FlightSearchResultAdapter(private var listFligth : List<Flight>) : RecyclerView.Adapter<FlightSearchResultAdapter.ViewHolder>() {
    class ViewHolder(var binding : ItemDataFlightBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemDataFlightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listFligth.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listPosition = listFligth[position]

        holder.binding.tvTimeDeparture.text = listPosition.timeDeparture
        holder.binding.tvTimeArrival.text = listPosition.timeArrive
        holder.binding.tvFrom.text = listPosition.from
        holder.binding.tvTo.text = listPosition.to
        val price = "IDR ${listPosition.price}"
        holder.binding.tvPriceFlight.text = price
        val airlineAndSeatClass = "${listPosition.airline} - ${listPosition.seatClass}"
        holder.binding.tvAirlineAndSeatClass.text = airlineAndSeatClass

    }
}