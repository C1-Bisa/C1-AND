package com.binar.finalproject.view.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.finalproject.R
import com.binar.finalproject.databinding.DialogFilterHasilPenerbanganBinding
import com.binar.finalproject.databinding.FragmentHasilPencarianBinding
import com.binar.finalproject.model.DateDeparture
import com.binar.finalproject.model.flight.Flight
import com.binar.finalproject.model.searchflight.Berangkat
import com.binar.finalproject.model.searchflight.SearchFlight
import com.binar.finalproject.view.adapter.DepartureDateAdapter
import com.binar.finalproject.view.adapter.FlightSearchResultAdapter
import com.binar.finalproject.viewmodel.FlightViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class HasilPencarianFragment : Fragment() {

    private lateinit var binding: FragmentHasilPencarianBinding

    //Adapter
    private lateinit var departureDateAdapter: DepartureDateAdapter
    private lateinit var flightSearchResultAdapter: FlightSearchResultAdapter

    //Viewmodel
    private val flightViewModel : FlightViewModel by viewModels()

    //filter termurah / termahal
    private var sortFromCheap : Boolean = true

    //search
    private var dataSearchFlight = SearchFlight("2023-09-12","00:00","Bali","","Jakarta")

    //jika search pulang pergi maka menggunakan list dengan type data generik
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

        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.visibility = View.GONE

        //nanti get bundle dari fragment home berupa data class dari input passenger
//        val data = SearchFlight("2023-09-14","00:00","Bali","","Jakarta")
        //cth
        //GET BUNDLE FROM HOME FRAGMENT
        val getDataSearch = arguments?.getSerializable("DATA_SEARCH")
        val getPassenger = arguments?.getString("DATA_PASSENGER")
        val getSeatClass = arguments?.getString("DATA_SEATCLASS")
        if(getDataSearch != null){
            dataSearchFlight = getDataSearch as SearchFlight

            Log.i("DATA_RESULT", getDataSearch.toString() + dataSearchFlight.toString())
            setRecycleViewDate()
            setRvFlightSearchResult()
            getAllDataFlight(dataSearchFlight)

            //set text appbar
            val titleBar = "${getDataSearch.from} > ${getDataSearch.to} - $getPassenger - $getSeatClass"
            binding.tvPencarian.text = titleBar
        }

        //menampilkan recycleview date departure


        //menampilkan dialog filter hasil penerbangan
        binding.btnFilter.setOnClickListener {
            showDialogFilter()
        }

        //mencari harga tiket termurah atau termahal
        binding.btnFilterHarga.setOnClickListener {
            sortListItem(sortFromCheap)
        }

        binding.btnUbahPencarian.setOnClickListener {
            findNavController().navigate(R.id.action_hasilPencarianFragment_to_homeFragment)
        }


    }

    private fun sortListItem(sortFromCheap: Boolean) {
        flightViewModel.getDataFlight(dataSearchFlight)

        flightViewModel.dataFlight.observe(viewLifecycleOwner){ it ->
            if(it.isNotEmpty()){
                var sortList : kotlin.collections.List<Berangkat> = emptyList()
                if(sortFromCheap){
                    sortList = it.sortedByDescending { it.price }
                    this.sortFromCheap = false
                }else{
                    sortList = it.sortedBy { it.price }
                    this.sortFromCheap = true
                }
                flightSearchResultAdapter.setListFlight(sortList)
            }
        }
    }

    private fun setRvFlightSearchResult() {

        flightSearchResultAdapter = FlightSearchResultAdapter(ArrayList())

        binding.rvDataFlight.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = flightSearchResultAdapter
        }


    }

    private fun getAllDataFlight(dataSearch : SearchFlight){
        flightViewModel.getDataFlight(dataSearch)

        flightViewModel.dataFlight.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                flightSearchResultAdapter.setListFlight(it)
                binding.layoutSearchNotFound.visibility = View.GONE
            }else{
                //masih terjadi kendala seharusnya data empty namun masih ada satu item list
                flightSearchResultAdapter.setListFlight(emptyList())
                binding.layoutSearchNotFound.visibility = View.VISIBLE
            }
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

        val inputDate = LocalDate.parse(dataSearchFlight.departureDate)
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
        val list = mutableListOf<DateDeparture>()
        for (i in listDate.indices){
            list.add(DateDeparture(listDay[i], listDate[i].toString()))
        }
        departureDateAdapter = DepartureDateAdapter(list)

        departureDateAdapter.dateDeparture = dataSearchFlight.departureDate
        binding.rvDateFlight.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = departureDateAdapter
        }

        departureDateAdapter.clickItemDate ={
            departureDateAdapter.dateDeparture = it.date
            departureDateAdapter.setListDate(list)
            //change date departure
            dataSearchFlight.departureDate = it.date
            getAllDataFlight(dataSearchFlight)
            Log.d("HASIL_PERUBAHAN_DATE", dataSearchFlight.toString())
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

    private fun setDate(inputDate: LocalDate, fristIndex : Int, lastIndex : Int) : MutableList<LocalDate>{
        val listDate = mutableListOf<LocalDate>()
        for (i in fristIndex..lastIndex) {
            val date = inputDate.plusDays(i.toLong())
            listDate.add(date)
        }
        return listDate
    }

}