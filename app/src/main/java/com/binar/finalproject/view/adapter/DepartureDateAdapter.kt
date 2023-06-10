package com.binar.finalproject.view.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.finalproject.databinding.ItemDateFlightBinding
import com.binar.finalproject.model.DateDeparture

class DepartureDateAdapter(private var listDate : List<DateDeparture>) : RecyclerView.Adapter<DepartureDateAdapter.ViewHolder>(){

    var dateDeparture : String? = null

    var clickItemDate : ((DateDeparture)->Unit)? = null

    class ViewHolder(var binding : ItemDateFlightBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemDateFlightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return listDate.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvDay.text = listDate[position].day
        holder.binding.tvDate.text = listDate[position].date

        if(dateDeparture == listDate[position].date){
            holder.binding.itemCardDateFlight.setCardBackgroundColor(Color.parseColor("#A06ECE"))
            holder.binding.tvDate.setTextColor(Color.parseColor("#FFFFFF"))
            holder.binding.tvDay.setTextColor(Color.parseColor("#FFFFFF"))
        }else{
            holder.binding.itemCardDateFlight.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            holder.binding.tvDate.setTextColor(Color.parseColor("#151515"))
            holder.binding.tvDay.setTextColor(Color.parseColor("#8A8A8A"))
        }

        holder.binding.itemCardDateFlight.setOnClickListener {
            clickItemDate?.invoke(listDate[position])
        }

    }

    fun setListDate(list: List<DateDeparture>){
        listDate = list
        notifyDataSetChanged()
    }
}