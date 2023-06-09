package com.binar.finalproject.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.finalproject.databinding.ItemRiwayatBinding
import com.binar.finalproject.model.RiwayatModel

class AdapterRiwayat(private val listRiwayat: List<RiwayatModel>):
    RecyclerView.Adapter<AdapterRiwayat.ViewHolder>() {

    class ViewHolder(var binding : ItemRiwayatBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterRiwayat.ViewHolder {
        val view = ItemRiwayatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterRiwayat.ViewHolder, position: Int) {
        holder.binding.txtLabelPembayaran.text = listRiwayat[position].textLabel
        holder.binding.namaKotaKiri.text = listRiwayat[position].lokasiKota
        holder.binding.tanggalKiri.text = listRiwayat[position].tgl
        holder.binding.jamKeberangkatanKiri.text = listRiwayat[position].time
        holder.binding.jarakTempuh.text = listRiwayat[position].jrkTempuh
        holder.binding.namaKotaKanan.text = listRiwayat[position].lokasiKota
        holder.binding.tanggalKanan.text = listRiwayat[position].tgl
        holder.binding.jamKeberangkatanKanan.text = listRiwayat[position].time

        holder.binding.txtBookingKode.text = listRiwayat[position].textBookkingCode
        holder.binding.codeBooking.text = listRiwayat[position].kodeBooking.toString()
        holder.binding.txtKelasPenerbangan.text = listRiwayat[position].txtKelas
        holder.binding.classPlane.text = listRiwayat[position].dataKelasPesawat
        holder.binding.tvPriceFlightRiwayat.text = listRiwayat[position].hargaRiwayatTiket



    }

    override fun getItemCount(): Int {
        return listRiwayat.size
    }
}