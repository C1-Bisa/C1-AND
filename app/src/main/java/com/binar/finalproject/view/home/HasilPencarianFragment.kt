package com.binar.finalproject.view.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.finalproject.R
import com.binar.finalproject.databinding.DialogFilterHasilPenerbanganBinding
import com.binar.finalproject.databinding.FragmentHasilPencarianBinding
import com.binar.finalproject.databinding.PassangerDialogLayoutBinding
import com.binar.finalproject.model.DateDeparture
import com.binar.finalproject.model.Flight
import com.binar.finalproject.view.adapter.DepartureDateAdapter
import com.binar.finalproject.view.adapter.FlightSearchResultAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*


class HasilPencarianFragment : Fragment() {

    private lateinit var binding: FragmentHasilPencarianBinding

//    Adapter
    private lateinit var departureDateAdapter: DepartureDateAdapter
    private lateinit var flightSearchResultAdapter: FlightSearchResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHasilPencarianBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //menampilkan recycleview date departure
        setRecycleViewDate()

        setRvFlightSearchResult()

        //menampilkan dialog filter hasil penerbangan
        binding.btnFilter.setOnClickListener {
            showDialogFilter()
        }

        //mencari harga tiket termurah atau termahal
        binding.btnFilterHarga.setOnClickListener {

        }


    }

    private fun setRvFlightSearchResult() {
        flightSearchResultAdapter = FlightSearchResultAdapter(listOf(
            Flight(1, "Lion Air", "Frist Class", "CGK", "DJJ", "11:00","15:00","23", "23", "500.000"),
            Flight(1, "Lion Air", "Frist Class", "CGK", "DJJ", "11:00","15:00","23", "23", "500.000"),
            Flight(1, "Lion Air", "Frist Class", "CGK", "DJJ", "11:00","15:00","23", "23", "500.000"),
            Flight(1, "Lion Air", "Frist Class", "CGK", "DJJ", "11:00","15:00","23", "23", "500.000"),
            Flight(1, "Lion Air", "Frist Class", "CGK", "DJJ", "11:00","15:00","23", "23", "500.000")
        ))

        binding.rvDataFlight.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = flightSearchResultAdapter
        }
    }

    //menampilkan dialog filter
    private fun showDialogFilter() {
        val dialog = BottomSheetDialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_filter_hasil_penerbangan)
        val bindingDialogFilter = DialogFilterHasilPenerbanganBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialogFilter.root)
        //
        bindingDialogFilter.btnClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation;
        dialog.window?.setGravity(Gravity.BOTTOM);
    }

    //masih perlu difixan antara menggunakan tab layout atau recycleview
    //menampilkan list filter date
    private fun setRecycleViewDate() {

        val inputDate = LocalDate.parse("2023-06-15")
        var listDate = mutableListOf<LocalDate>()
        var listDay = mutableListOf<String>()

        if (LocalDate.now() == inputDate){
            listDate = setDate(inputDate,0,6)
        }else{
            listDate = setDate(inputDate, -3, 3)
        }

        listDay = setDay(listDate)


        Log.i("DAY" , listDay.toString())
        Log.i("TANGGAL" , listDate.toString())
        //tinggal dimasukkan kedalam list saja untuk day dan datenya
        val list = listOf(
            DateDeparture("Senin", "12/02/2023"),
            DateDeparture("Selasa", "13/02/2023"),
            DateDeparture("Rabu", "14/02/2023"),
            DateDeparture("Kamis", "15/02/2023"),
            DateDeparture("Jumat", "16/02/2023")
        )
        departureDateAdapter = DepartureDateAdapter(list)

        departureDateAdapter.dateDeparture = "14/02/2023"
        binding.rvDateFlight.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = departureDateAdapter
        }

        departureDateAdapter.clickItemDate ={
            departureDateAdapter.dateDeparture = it.date
            departureDateAdapter.setListDate(list)
//            Toast.makeText(context, "$it",Toast.LENGTH_SHORT).show()
        }



    }

    private fun setDay(listDate: MutableList<LocalDate>) : MutableList<String> {
        val listDay = mutableListOf<String>()
        for(i in listDate.indices){
            listDay.add(listDate[i].dayOfWeek.getDisplayName(TextStyle.FULL, Locale("id", "ID")))
        }
        return listDay
    }

    fun setDate(inputDate: LocalDate, fristIndex : Int, lastIndex : Int) : MutableList<LocalDate>{
        val listDate = mutableListOf<LocalDate>()
        for (i in fristIndex..lastIndex) {
            val date = inputDate.plusDays(i.toLong())
            listDate.add(date)
        }
        return listDate
    }

}