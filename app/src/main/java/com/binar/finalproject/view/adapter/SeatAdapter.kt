package com.binar.finalproject.view.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.finalproject.databinding.ItemNumRowsSeatBinding
import com.binar.finalproject.databinding.ItemSeatBinding
import com.binar.finalproject.model.seatconfiguration.Seat

class SeatAdapter(private var listSeat : List<Seat>, var numPassenger : Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listConfigurationSeat : MutableList<String> = mutableListOf()

    init {
        for (i in 1..numPassenger){
            listConfigurationSeat.add("")
        }
    }

    var itemSeatOnClick : ((Seat)->Unit)? =null

    class SeatViewHolder(var binding: ItemSeatBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    class RowSeatViewHolder(var binding : ItemNumRowsSeatBinding) : RecyclerView.ViewHolder(binding.root) {
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_SEAT -> {
                val itemView = ItemSeatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                SeatViewHolder(itemView)
            }
            ITEM_ROW_SEAT -> {
                val itemView = ItemNumRowsSeatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                RowSeatViewHolder(itemView)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return listSeat.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = listSeat[position]

        when (holder.itemViewType) {
            ITEM_SEAT -> {
                val seat = holder as SeatViewHolder
                seat.binding.tvNumSeat.text = item.seatCode

                if(!item.seatAvailable){
                    seat.binding.itemSeat.setBackgroundColor(Color.parseColor("#D0D0D0"))
                    seat.binding.itemSeat.isEnabled = false
                }

                seat.binding.itemSeat.setOnClickListener {
                    itemSeatOnClick?.invoke(item)
                    if(item.seatCode in listConfigurationSeat){
                        seat.binding.itemSeat.setBackgroundColor(Color.parseColor("#73CA5C"))
                        for (i in listConfigurationSeat.indices){
                            if(listConfigurationSeat[i] == item.seatCode){
                                listConfigurationSeat[i] = ""
                                seat.binding.tvNumSeat.text = item.seatCode
                                break
                            }
                        }
                    }else{
                        var seatNotEmpty = false
                        for(i in listConfigurationSeat.indices){
                            if(listConfigurationSeat[i].isEmpty()){
                                seatNotEmpty = true
                                break
                            }
                        }
                        if(seatNotEmpty){
                            for (i in listConfigurationSeat.indices){
                                if(listConfigurationSeat[i].isEmpty()){
                                    listConfigurationSeat[i] = item.seatCode
                                    val passNum = "P ${i+1}"
                                    seat.binding.tvNumSeat.text = passNum
                                    break
                                }
                            }
                            seat.binding.itemSeat.setBackgroundColor(Color.parseColor("#7126B5"))
                        }
                    }
                }
            }
            ITEM_ROW_SEAT -> {
                val row = holder as RowSeatViewHolder
                row.binding.tvRowSeat.text = item.seatCode
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        val listSeatRow = listOf<Int>( 3, 10, 17, 24, 31, 38, 45, 52, 59, 66, 73, 80)
        return if (position in listSeatRow) {
            ITEM_ROW_SEAT
        } else {
            ITEM_SEAT
        }
    }

    companion object {
        const val ITEM_SEAT = 1
        const val ITEM_ROW_SEAT = 2
    }

    fun setListSeat(list : List<Seat>){
        listSeat = list
        notifyDataSetChanged()
    }

    fun getConfigurationSeatPass() : List<String>{
        return listConfigurationSeat
    }
}