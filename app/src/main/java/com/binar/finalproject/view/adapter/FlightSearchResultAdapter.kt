package com.binar.finalproject.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.finalproject.databinding.ItemDataFlightBinding
import com.binar.finalproject.model.searchflight.Flight
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

//untuk type data pada list bisa menggunakan tipe data generik sehingga sesuai inputan pulan atau berangkat
class FlightSearchResultAdapter(private var listFligth : List<Flight>) : RecyclerView.Adapter<FlightSearchResultAdapter.ViewHolder>() {

    var onClickItemFlight : ((Flight)->Unit)? = null
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

        //time
        holder.binding.tvTimeDeparture.text = setFormatTimeFlight(listPosition.departureTime)
        holder.binding.tvTimeArrival.text = setFormatTimeFlight(listPosition.arrivalTime)
        //destination and location departure
        holder.binding.tvFrom.text = listPosition.from
        holder.binding.tvTo.text = listPosition.to
        //price
//        val price = "IDR ${NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(listPosition.price.toInt())}"
        val price = "IDR ${convertToCurrencyIDR(listPosition.price)}"
        holder.binding.tvPriceFlight.text = price
        //flight class
        val airlineAndSeatClass = "${listPosition.airline} - ${listPosition.flightClass}"
        holder.binding.tvAirlineAndSeatClass.text = airlineAndSeatClass
        //estimation duration flight (di json sudah ada data duration tinggal diganti)
        holder.binding.tvDurationFlight.text = setFlightDuration(listPosition.departureTime, listPosition.arrivalTime)

        //onclick
        holder.binding.itemFlight.setOnClickListener {
            onClickItemFlight?.invoke(listPosition)
        }

    }

    private fun setFlightDuration(departureTime : String, arrivalTime : String) : String{

        val formatTime = DateTimeFormatter.ofPattern("HH:mm:ss", Locale("id"))

        val formatDepartureTime = LocalTime.parse(departureTime, formatTime)
        val formatArrivalTime = LocalTime.parse(arrivalTime, formatTime)

        val estimationDurationFlight = Duration.between(formatDepartureTime, formatArrivalTime)
        val hours = estimationDurationFlight.toHours()
        val minutes = estimationDurationFlight.toMinutes()

        return "${hours}h ${minutes%60}m"
    }

    private fun setFormatTimeFlight(time: String): String {

        val formatTime = LocalTime.parse(time, DateTimeFormatter.ISO_LOCAL_TIME)

        return formatTime.format(DateTimeFormatter.ofPattern("HH:mm"))
    }

    private fun convertToCurrencyIDR(price : Int) : String{
        val currencyFormatIDR = DecimalFormat("#,###", DecimalFormatSymbols.getInstance(Locale("id", "ID")))
        return currencyFormatIDR.format(price)

    }

    fun setListFlight(list : List<Flight>){
        listFligth = list
        notifyDataSetChanged()
    }
}