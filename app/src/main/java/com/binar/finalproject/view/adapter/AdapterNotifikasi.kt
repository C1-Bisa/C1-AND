package com.binar.finalproject.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.finalproject.databinding.ItemNotifikasiBinding
import com.binar.finalproject.model.Notifikasi

class AdapterNotifikasi(private val listNotifkasi : List<Notifikasi>):
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
        holder.binding.tvPromo.text = listNotifkasi[position].typeNotif
        holder.binding.tanggal.text = listNotifkasi[position].tgl
        holder.binding.DetailPromo.text = listNotifkasi[position].title
        holder.binding.syaratketentuan.text = listNotifkasi[position].tagLine
    }

    override fun getItemCount(): Int {
        return listNotifkasi.size
    }
}