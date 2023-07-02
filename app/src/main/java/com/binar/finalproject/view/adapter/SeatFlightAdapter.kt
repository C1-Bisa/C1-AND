package com.binar.finalproject.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.binar.finalproject.databinding.ItemSeatConfigurateBinding
import com.binar.finalproject.model.seatconfiguration.ConfigurationSeat
import com.binar.finalproject.model.seatconfiguration.Seat

class SeatFlightAdapter(private var list : List<ConfigurationSeat>, private var numPassenger : Int, private var typeRoundTrip : Boolean) : RecyclerView.Adapter<SeatFlightAdapter.ViewHolder>() {

    private var seatAdapter : SeatAdapter = SeatAdapter(emptyList(), numPassenger)
    private var listSeatFlight : MutableList<List<String>> = mutableListOf()

    init {
        if(typeRoundTrip){
            for (i in 0 until 2){
                listSeatFlight.add(listOf())
            }
        }else{
            listSeatFlight.add(listOf())
        }
    }


    class ViewHolder(var binding: ItemSeatConfigurateBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = ItemSeatConfigurateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val title = "${list[position].title} - ${list[position].flightClass} - 64 Kursi Tersedia"
        holder.binding.tvClassAndAviableSeat.text = title

        seatAdapter = SeatAdapter(setListSeat(), numPassenger)
        holder.binding.rvSeat.apply {
            layoutManager = GridLayoutManager(context, 7)
            adapter = seatAdapter
        }

        listSeatFlight[position] = seatAdapter.getConfigurationSeatPass()
    }

    fun getSeat() : List<List<String>>{
        return listSeatFlight
    }

    private fun setListSeat() : List<Seat>{
        return listOf(
            Seat("A1", true) ,
            Seat("B1", false),
            Seat("C1", true),
            Seat("1", true),
            Seat("D1", true),
            Seat("E1", false),
            Seat("F1", true),
            Seat("A2", true) ,
            Seat("B2", false),
            Seat("C2", false),
            Seat("2", true),
            Seat("D2", true),
            Seat("E2", false),
            Seat("F2", true),
            Seat("A3", false) ,
            Seat("B3", false),
            Seat("C3", true),
            Seat("3", true),
            Seat("D3", false),
            Seat("E3", true),
            Seat("F3", false),
            Seat("A4", true) ,
            Seat("B4", false),
            Seat("C4", false),
            Seat("4", true),
            Seat("D4", true),
            Seat("E4", false),
            Seat("F4", false),
            Seat("A5", true) ,
            Seat("B5", false),
            Seat("C5", true),
            Seat("5", true),
            Seat("D5", true),
            Seat("E5", false),
            Seat("F5", false),
            Seat("A6", true) ,
            Seat("B6", false),
            Seat("C6", true),
            Seat("6", true),
            Seat("D6", true),
            Seat("E6", false),
            Seat("F6", true),
            Seat("A7", false) ,
            Seat("B7", false),
            Seat("C7", false),
            Seat("7", true),
            Seat("D7", false),
            Seat("E7", false),
            Seat("F7", true),
            Seat("A8", true) ,
            Seat("B8", false),
            Seat("C8", true),
            Seat("8", true),
            Seat("D8", true),
            Seat("E8", false),
            Seat("F8", true),
            Seat("A9", true) ,
            Seat("B9", false),
            Seat("C9", true),
            Seat("9", true),
            Seat("D9", false),
            Seat("E9", false),
            Seat("F9", true),
            Seat("A10", true) ,
            Seat("B10", true),
            Seat("C10", true),
            Seat("10", true),
            Seat("D10", false),
            Seat("E10", true),
            Seat("F10", true),
            Seat("A11", true) ,
            Seat("B11", false),
            Seat("C11", true),
            Seat("11", true),
            Seat("D11", true),
            Seat("E11", false),
            Seat("F11", true),
            Seat("A12", true) ,
            Seat("B12", true),
            Seat("C12", true),
            Seat("12", true),
            Seat("D12", false),
            Seat("E12", true),
            Seat("F12", true)
        )
    }
}