package com.binar.finalproject.view.adapter

import android.annotation.SuppressLint
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
            holder.binding.itemCardDateFlight.setCardBackgroundColor(Color.parseColor("#00AD10"))
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

    @SuppressLint("NotifyDataSetChanged")
    fun setListDate(list: List<DateDeparture>){
        listDate = list
        notifyDataSetChanged()
    }
}