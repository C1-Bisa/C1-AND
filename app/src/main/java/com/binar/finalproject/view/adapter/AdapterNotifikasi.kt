package com.binar.finalproject.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.finalproject.R
import com.binar.finalproject.databinding.ItemNotifikasiBinding
import com.binar.finalproject.model.Notifikasi
import com.binar.finalproject.model.notification.responsegetnotif.Data
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AdapterNotifikasi(private var listNotifkasi : List<Data>):
    RecyclerView.Adapter<AdapterNotifikasi.ViewHolder>() {

    class ViewHolder(var binding : ItemNotifikasiBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterNotifikasi.ViewHolder {
        val view = ItemNotifikasiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterNotifikasi.ViewHolder, position: Int) {
        holder.binding.tvTypeNotif.text = listNotifkasi[position].headNotif
        holder.binding.tanggal.text = setDate(listNotifkasi[position].createdAt)
        holder.binding.DetailMessage.text = listNotifkasi[position].message

        if(listNotifkasi[position].isRead){
            holder.binding.pointer.setImageResource(R.drawable.point_hijau)
        }else{
            holder.binding.pointer.setImageResource(R.drawable.point_merah)
        }
    }

    private fun setDate(date : String) : String{
        val zonedDateTimeParse = ZonedDateTime.parse(date)
        val localDateTimeAsia = zonedDateTimeParse.withZoneSameInstant(ZoneId.of("Asia/Jakarta")).toLocalDateTime()
        val dateFormatterId = DateTimeFormatter.ofPattern("dd MMMM yyyy, hh:mm", Locale("id"))
        return localDateTimeAsia.format(dateFormatterId)
    }

    override fun getItemCount(): Int {
        return listNotifkasi.size
    }

    fun setListNotif(list : List<Data>){
        listNotifkasi = list
        notifyDataSetChanged()
    }
}