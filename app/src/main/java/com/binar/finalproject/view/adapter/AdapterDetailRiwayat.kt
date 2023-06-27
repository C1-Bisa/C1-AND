package com.binar.finalproject.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.finalproject.databinding.ItemDetailFlightBinding
import com.binar.finalproject.databinding.ItemDetailRiwayatBinding
import com.binar.finalproject.model.gethistory.detailhistory.ResponseDetailTransactionHistory

class AdapterDetailRiwayat(private var listDetailHistory: List<ResponseDetailTransactionHistory> ) : RecyclerView.Adapter<AdapterDetailRiwayat.ViewHolder>(){
    class ViewHolder(var binding : ItemDetailRiwayatBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterDetailRiwayat.ViewHolder {
        val view = ItemDetailRiwayatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterDetailRiwayat.ViewHolder, position: Int) {
        val listPosition = listDetailHistory[position]

        holder.binding.tvNoBookingCode.text = listPosition.data.transaction.transactionCode


    }

    override fun getItemCount(): Int {
        return listDetailHistory.size
    }
}