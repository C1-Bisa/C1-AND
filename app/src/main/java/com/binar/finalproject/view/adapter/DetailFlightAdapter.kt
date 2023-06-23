package com.binar.finalproject.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.finalproject.databinding.ItemDetailFlightBinding
import com.binar.finalproject.model.getdetailflight.ListDetailFlight
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DetailFlightAdapter(private var listData : List<ListDetailFlight>) : RecyclerView.Adapter<DetailFlightAdapter.ViewHolder>(){
    class ViewHolder(var binding : ItemDetailFlightBinding) : RecyclerView.ViewHolder(binding.root) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemDetailFlightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listPosition = listData[position]

        val destinationFlight = "${listPosition.from } - ${listPosition.to}"
        holder.binding.tvFlightDestination.text = destinationFlight

        val durationFlight = "(${listPosition.duration/60}h ${listPosition.duration%60}m)"
        holder.binding.tvFlightTime.text = durationFlight

        holder.binding.tvTimeDeparture.text = timeFormate(listPosition.departureTime)

        holder.binding.tvDateDeparture.text = setDate(listPosition.departureDate)

        holder.binding.tvDepartureAirport.text = listPosition.airportFrom.airportName

        val airlineAndClass = "${listPosition.airline.airlineName} - ${listPosition.flightClass}"
        holder.binding.tvAirline.text = airlineAndClass

        holder.binding.tvDescription.text = setDescription(listPosition.description)

        holder.binding.tvTimeArrive.text = timeFormate(listPosition.arrivalTime)

        holder.binding.tvDateArrive.text = setDate(listPosition.arrivalDate)

        holder.binding.tvArriveAirport.text = listPosition.airportTo.airportName

    }

    private fun setDescription(des : String) : String{
        return des.replace("kg", "kg\n")
    }
    private fun convertToCurrencyIDR(price : Int) : String{
        val currencyFormatIDR = DecimalFormat("#,###", DecimalFormatSymbols.getInstance(Locale("id", "ID")))
        return currencyFormatIDR.format(price)

    }

    //untuk mengubah tima format (hh:mm:ss) ke (hh:mm)
    private fun timeFormate(time : String) : String{
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        val parsedTimeLocal = LocalTime.parse(time, timeFormatter)
        return parsedTimeLocal.format(DateTimeFormatter.ofPattern("HH:mm"))
    }

    private fun setDate(date : String) : String{
        val zonedDateTimeParse = ZonedDateTime.parse(date)
        val localDateTimeAsia = zonedDateTimeParse.withZoneSameInstant(ZoneId.of("Asia/Jakarta")).toLocalDateTime()
        val dateFormatterId = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("id"))
        return localDateTimeAsia.format(dateFormatterId)
    }

    fun setListFlight(list : List<ListDetailFlight>){
        listData = list
        notifyDataSetChanged()
    }
}