package com.binar.finalproject.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.finalproject.databinding.ItemPassengerBinding
import com.binar.finalproject.model.transactionhistoryperid.response.DataPassenger

class PassengerAdapter(private var listData : List<DataPassenger>) : RecyclerView.Adapter<PassengerAdapter.ViewHolder>() {

    class ViewHolder(var binding : ItemPassengerBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemPassengerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listData[position]
        val numPass = "Penumpang ${position+1} "
        val namePass = "${data.title} ${data.name}"
        holder.binding.apply {
            tvNumPassenger.text = numPass

            tvPassengerRincianRiwayat.text = namePass

            tvNumIDPassenger.text = data.nikPaspor
        }
    }

    fun setListPassAdapter(list: List<DataPassenger>){
        listData = list
        notifyDataSetChanged()
    }
}