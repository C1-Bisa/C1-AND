package com.binar.finalproject.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.binar.finalproject.R
import com.binar.finalproject.databinding.ItemFormBiodataPenumpangBinding
import com.binar.finalproject.model.BiodataPassenger

class BiodataPassengerAdapter(private var listBio : List<BiodataPassenger>, val context: Context) : RecyclerView.Adapter<BiodataPassengerAdapter.ViewHolder>() {

    private val arrayAdapterTitle = ArrayAdapter(context, R.layout.item_list_title_passenger, listOf("Mr", "Ms"))

    private var listInput = mutableListOf<BiodataPassenger>()

    init {
        for (i in listBio.indices) {
            listInput.add(BiodataPassenger("", ""))
        }
    }

    class ViewHolder(var binding : ItemFormBiodataPenumpangBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = ItemFormBiodataPenumpangBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listBio.size
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val listData = listBio[position]
        val category = "Passenger ${position+1} - ${listData.ageCategory}"
        holder.binding.tvPassengers.text = category
        holder.binding.titlePassenger.setAdapter(arrayAdapterTitle)
        //add age categori
        listInput[position].ageCategory = listBio[position].ageCategory
        holder.binding.optionClan.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                holder.binding.layoutNameClan.visibility = View.VISIBLE
            }else{
                holder.binding.layoutNameClan.visibility = View.GONE
            }
        }
        holder.binding.etNamaLengkapPenumpang.addTextChangedListener(
            object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    listInput[position].namePassenger = p0.toString()
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    listInput[position].namePassenger = p0.toString()
                }
                override fun afterTextChanged(p0: Editable?) {}
            }
        )

        listInput[position].titlePassenger = holder.binding.titlePassenger.text.toString()

        holder.binding.titlePassenger.addTextChangedListener(
            object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    listInput[position].titlePassenger = p0.toString()
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    listInput[position].titlePassenger = p0.toString()
                }
                override fun afterTextChanged(p0: Editable?) {}
            }
        )

        holder.binding.etNameClan.addTextChangedListener(
            object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    listInput[position].nameClan = p0.toString()
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    listInput[position].nameClan = p0.toString()
                }
                override fun afterTextChanged(p0: Editable?) {}
            }
        )

        holder.binding.etDateOfBirthPassenger.addTextChangedListener(
            object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    listInput[position].birthDate = p0.toString()
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    listInput[position].birthDate = p0.toString()
                }
                override fun afterTextChanged(p0: Editable?) {}
            }
        )

        holder.binding.etCitizenship.addTextChangedListener(
            object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    listInput[position].citizen = p0.toString()
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    listInput[position].citizen = p0.toString()
                }
                override fun afterTextChanged(p0: Editable?) {}
            }
        )

        holder.binding.etIDorPassport.addTextChangedListener(
            object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    listInput[position].idCardOrPassport = p0.toString()
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    listInput[position].idCardOrPassport = p0.toString()
                }
                override fun afterTextChanged(p0: Editable?) {}
            }
        )

        holder.binding.etIssuingCountry.addTextChangedListener(
            object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    listInput[position].issuingCountry = p0.toString()
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    listInput[position].issuingCountry = p0.toString()
                }
                override fun afterTextChanged(p0: Editable?) {}
            }
        )

        holder.binding.tvPassengers.setOnClickListener {
            Toast.makeText(context, listInput.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    fun getDataBioPassenger() : List<BiodataPassenger>{
        return listInput
    }
}