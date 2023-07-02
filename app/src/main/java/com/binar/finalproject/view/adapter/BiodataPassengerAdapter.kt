package com.binar.finalproject.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.binar.finalproject.R
import com.binar.finalproject.databinding.ItemFormBiodataPenumpangBinding
import com.binar.finalproject.model.BiodataPassenger
import com.binar.finalproject.model.transaction.request.Passenger
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class BiodataPassengerAdapter(private var listBio : List<BiodataPassenger>, val context: Context, val activity : FragmentActivity) : RecyclerView.Adapter<BiodataPassengerAdapter.ViewHolder>() {

    private val arrayAdapterTitle = ArrayAdapter(context, R.layout.item_list_title_passenger, listOf("Mr.", "Ms."))

    private var listInput = mutableListOf<Passenger>()

    init {
        for (i in listBio.indices) {
            listInput.add(Passenger("","","", "","","",0,"",listBio[i].ageCategory, "",""))
        }
    }

    class ViewHolder(var binding : ItemFormBiodataPenumpangBinding) : RecyclerView.ViewHolder(binding.root) {
        fun datePicker(activity: FragmentActivity, etBirth: EditText){
            setBirthPassenger(activity, etBirth)
        }

        private fun setBirthPassenger(activity: FragmentActivity, etBirth: EditText){
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()

            datePicker.show(activity.supportFragmentManager, "DatePickerDialog")

            var dateBirth: String

            datePicker.addOnPositiveButtonClickListener {
                val dateInput = Date(it)

                val dateFormatBirth = SimpleDateFormat("yyyy-MM-dd", Locale("id","ID"))

                dateBirth = dateFormatBirth.format(dateInput)

                etBirth.setText(dateBirth)
                Log.i("DATE BIRTH", dateBirth)
            }

        }
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
        listInput[position].type = listBio[position].ageCategory
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
                    listInput[position].name = p0.toString()
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    listInput[position].name = p0.toString()
                }
                override fun afterTextChanged(p0: Editable?) {}
            }
        )

        listInput[position].title = holder.binding.titlePassenger.text.toString()

        holder.binding.titlePassenger.addTextChangedListener(
            object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    listInput[position].title = p0.toString()
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    listInput[position].title = p0.toString()
                }
                override fun afterTextChanged(p0: Editable?) {}
            }
        )

        holder.binding.etNameClan.addTextChangedListener(
            object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    listInput[position].familyName = p0.toString()
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    listInput[position].familyName = p0.toString()
                }
                override fun afterTextChanged(p0: Editable?) {}
            }
        )

        holder.binding.etDateOfBirthPassenger.addTextChangedListener(
            object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    listInput[position].birthday = p0.toString()
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    listInput[position].birthday = p0.toString()
                }
                override fun afterTextChanged(p0: Editable?) {}
            }
        )

        holder.binding.etCitizenship.addTextChangedListener(
            object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    listInput[position].nationality = p0.toString()
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    listInput[position].nationality = p0.toString()
                }
                override fun afterTextChanged(p0: Editable?) {}
            }
        )

        holder.binding.etIDorPassport.addTextChangedListener(
            object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if(p0 != null && p0.isNotEmpty()){
                        listInput[position].nik = p0.toString().toInt()
                    }
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if(p0 != null && p0.isNotEmpty()){
                        listInput[position].nik = p0.toString().toInt()
                    }
                }
                override fun afterTextChanged(p0: Editable?) {
                    if(p0 != null && p0.isNotEmpty()){
                        listInput[position].nik = p0.toString().toInt()
                    }
                }
            }
        )

        holder.binding.etIssuingCountry.addTextChangedListener(
            object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    listInput[position].issuedCountry = p0.toString()
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    listInput[position].issuedCountry = p0.toString()
                }
                override fun afterTextChanged(p0: Editable?) {}
            }
        )

        holder.binding.etExpired.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                listInput[position].expired = p0.toString()
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                listInput[position].expired = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        holder.binding.tvPassengers.setOnClickListener {
            Toast.makeText(context, listInput.toString(), Toast.LENGTH_SHORT).show()
        }

        holder.binding.etDateOfBirthPassenger.setOnClickListener {
            //add regex
            //val regexPattern = "^\\d{4}-\\d{2}-\\d{2}$".toRegex()
            holder.datePicker(activity, holder.binding.etDateOfBirthPassenger)
        }

        holder.binding.etExpired.setOnClickListener {
            holder.datePicker(activity, holder.binding.etExpired)
        }
    }

    fun getDataBioPassenger() : List<Passenger>{
        return listInput
    }
}