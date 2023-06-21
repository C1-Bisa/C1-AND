package com.binar.finalproject.view.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.finalproject.R
import com.binar.finalproject.databinding.DialogFilterHasilPenerbanganBinding
import com.binar.finalproject.databinding.FragmentHasilPencarianBinding
import com.binar.finalproject.model.DateDeparture
import com.binar.finalproject.model.searchflight.*
import com.binar.finalproject.view.adapter.DepartureDateAdapter
import com.binar.finalproject.view.adapter.FlightSearchResultAdapter
import com.binar.finalproject.viewmodel.FlightViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.http.Query
import java.text.SimpleDateFormat
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
    //post to search flight
    private var postSearchFlight = PostSearchFlight("2023-09-12","00:00","Economy","","Jakarta")
    private var dataSearchFlight = SearchFlight()

    //status apakah sedang mencari flight1 atau 2
    private var statusFlightReturnFlight : Boolean = false

    //inisialisasi data from bundle (home fragment)
    private var numPassenger : String = ""
    private var seatClass : String = ""
    private var listNumSeatPassenger : IntArray = IntArray(3)

    //khusus unutk round trip
    private var flightTicketRoundTrip = FlightTicketRoundTrip()
    private var flightTicketOneTrip = FlightTicketOneTrip()

    //initial filter type
    var setFilterIndex : Int? = null
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

        //set visibility layout load dan recycleview
        binding.layoutLoadingData.visibility = View.VISIBLE

        //nanti get bundle dari fragment home berupa data class dari input passenger
//        val data = SearchFlight("2023-09-14","00:00","Bali","","Jakarta")
        //cth
        //GET BUNDLE FROM HOME FRAGMENT
        val getDataSearch = arguments?.getSerializable("DATA_SEARCH")
        val getPassenger = arguments?.getString("DATA_PASSENGER")
        val getSeatClass = arguments?.getString("DATA_SEATCLASS")
        val getListSeatPassenger = arguments?.getIntArray("DATA_LIST_NUM_SEAT")

        val getPickFligtReturn = arguments?.getBoolean("STATUS_PICK_FLIGHT_RETURN")
        val getDataRoundTrip = arguments?.getSerializable("DATA_FLIGHT_ROUND_TRIP")

        if(getPickFligtReturn != null && getDataRoundTrip != null){
            statusFlightReturnFlight = getPickFligtReturn
            flightTicketRoundTrip = getDataRoundTrip as FlightTicketRoundTrip
            Log.i("STATUS_RETURN", statusFlightReturnFlight.toString())
        }

        if(getDataSearch != null){
            //app bar
            val titleBar : String
            //assignment
            dataSearchFlight = getDataSearch as SearchFlight
            //assignment list num passenger
            listNumSeatPassenger = getListSeatPassenger!!

            if(statusFlightReturnFlight){
                //departure time sementara nanti diganti dengan jam pemilihan tanggal
                postSearchFlight = PostSearchFlight(dataSearchFlight.returnDate,
                    "08:00",
                    getSeatClass.toString(),
                    dataSearchFlight.to,
                    dataSearchFlight.from)

                Log.i("DATA_RESULT", dataSearchFlight.toString())
                Log.i("DATA_RESULT_POST_FLIGHT", postSearchFlight.toString())
                setRecycleViewDate()
                setRvFlightSearchResult()
                getAllDataFlight(postSearchFlight)

                titleBar = "${getDataSearch.to} > ${getDataSearch.from} - $getPassenger - $getSeatClass"
            }else{

                postSearchFlight = PostSearchFlight(dataSearchFlight.departureDate,
                    "08:00",
                    getSeatClass.toString(),
                    dataSearchFlight.from,
                    dataSearchFlight.to)

                Log.i("DATA_RESULT", dataSearchFlight.toString())
                Log.i("DATA_RESULT_POST_FLIGHT", postSearchFlight.toString())
                setRecycleViewDate()
                setRvFlightSearchResult()
                getAllDataFlight(postSearchFlight)
                titleBar = "${getDataSearch.from} > ${getDataSearch.to} - $getPassenger - $getSeatClass"

            }
            //set text appbar
            binding.tvPencarian.text = titleBar

            //assignment
            numPassenger = getPassenger.toString()
            seatClass = getSeatClass.toString()
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
            //jangan menggunakan ini, namun menggunakan onbackpress
            findNavController().navigate(R.id.action_hasilPencarianFragment_to_homeFragment)
        }

        //btn back
        binding.btnBack.setOnClickListener {

            findNavController().navigateUp()
        }


    }

    private fun sortListItem(sortFromCheap: Boolean) {
        flightViewModel.getDataFlight(postSearchFlight)

        flightViewModel.dataFlight.observe(viewLifecycleOwner){ it ->
            if(it.isNotEmpty()){
                var sortList : kotlin.collections.List<Flight> = emptyList()
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


        //item click detail flight to fragment detail flight
        flightSearchResultAdapter.onClickDetailFlight = {
            if(it.toString().isNotEmpty()){
                val dataFlightBundle = Bundle().apply {
                    putSerializable("DATA_FLIGHT",it)
                    putSerializable("DATA_SEARCH", dataSearchFlight)
                    putString("DATA_PASSENGER", numPassenger)
                    putString("DATA_SEATCLASS", seatClass)
                    putBoolean("STATUS_PICK_FLIGHT_RETURN", statusFlightReturnFlight)
                    putSerializable("DATA_FLIGHT_ROUND_TRIP", flightTicketRoundTrip)
                    putIntArray("DATA_LIST_NUM_SEAT", listNumSeatPassenger)
                }
                findNavController().navigate(R.id.action_hasilPencarianFragment_to_detailPenerbanganFragment, dataFlightBundle)
            }
        }

        //pick flight
        flightSearchResultAdapter.onClickItemFlight = {
            if(it.id.toString().isNotEmpty() && dataSearchFlight.returnDate.isEmpty() && !statusFlightReturnFlight){
                flightTicketOneTrip.flightIdDeparture = it.id
                Log.i("ID_FLIGHT_DEPARTURE", flightTicketOneTrip.flightIdDeparture.toString())

                val putBundleDataFlight = Bundle().apply {
                    putIntArray("DATA_LIST_NUM_SEAT", listNumSeatPassenger)
                }
                findNavController().navigate(R.id.action_hasilPencarianFragment_to_biodataPemesanFragment, putBundleDataFlight)
            }else{
                if(!statusFlightReturnFlight){
                    flightTicketRoundTrip.flightIdDeparture = it.id
                    val putBundleFlight = Bundle().apply {
                        putBoolean("STATUS_PICK_FLIGHT_RETURN", true)
                        putSerializable("DATA_SEARCH", dataSearchFlight)
                        putString("DATA_PASSENGER", numPassenger)
                        putString("DATA_SEATCLASS", seatClass)
                        putSerializable("DATA_FLIGHT_ROUND_TRIP", flightTicketRoundTrip)
                        putIntArray("DATA_LIST_NUM_SEAT", listNumSeatPassenger)
                    }
                    Log.i("ID_FLIGHT_DEPARTURE", flightTicketRoundTrip.toString())
                    findNavController().navigate(R.id.action_call_self, putBundleFlight)
                }else{
                    flightTicketRoundTrip.flightIdReturn = it.id
                    Log.i("ID_FLIGHT_DEPARTURE", flightTicketRoundTrip.toString())

                    val putBundleDataFlight = Bundle().apply {
                        putIntArray("DATA_LIST_NUM_SEAT", listNumSeatPassenger)
                    }

                    findNavController().navigate(R.id.action_hasilPencarianFragment_to_biodataPemesanFragment, putBundleDataFlight)
                }
            }
        }

    }

    private fun getAllDataFlight(dataSearch : PostSearchFlight){
        flightViewModel.getDataFlight(dataSearch)

        flightViewModel.dataFlight.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                flightSearchResultAdapter.setListFlight(it)
                binding.layoutSearchNotFound.visibility = View.GONE
                //set visibility layout load
                binding.layoutLoadingData.visibility = View.GONE
            }else{
                //masih terjadi kendala seharusnya data empty namun masih ada satu item list
                flightSearchResultAdapter.setListFlight(emptyList())
                binding.layoutSearchNotFound.visibility = View.VISIBLE
                //set visibility layout load
                binding.layoutLoadingData.visibility = View.GONE
            }
        }

//        flightSearchResultAdapter.onClickItemFlight = {
//            findNavController().navigate(R.id.action_hasilPencarianFragment_to_detailPenerbanganFragment)
//        }
    }

    //menampilkan dialog filter
    private fun showDialogFilter() {
        val dialog = BottomSheetDialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_filter_hasil_penerbangan)
        val bindingDialogFilter = DialogFilterHasilPenerbanganBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialogFilter.root)


        bindingDialogFilter.layoutHargaTermurah.setOnClickListener {
            setHoverFilter(bindingDialogFilter, 0)
            setFilterIndex = 0
        }

        bindingDialogFilter.layoutKeberangkatanAwal.setOnClickListener {
            setHoverFilter(bindingDialogFilter, 1)
            setFilterIndex = 1
        }

        bindingDialogFilter.layoutKeberangkatanAkhir.setOnClickListener {
            setHoverFilter(bindingDialogFilter, 2)
            setFilterIndex = 2
        }

        bindingDialogFilter.layoutKedatanganAwal.setOnClickListener {
            setHoverFilter(bindingDialogFilter, 3)
            setFilterIndex = 3
        }

        bindingDialogFilter.layoutKedatanganAkhir.setOnClickListener {
            setHoverFilter(bindingDialogFilter, 4)
            setFilterIndex = 4
        }
        //
        bindingDialogFilter.btnClose.setOnClickListener {
            dialog.dismiss()
        }

        bindingDialogFilter.btnSaveFilter.setOnClickListener {
            if(setFilterIndex != null){
                setFilterFlight(setFilterIndex!!)
            }

            dialog.dismiss()

        }

        //set hover default
        if(setFilterIndex != null){
            setHoverFilter(bindingDialogFilter, setFilterIndex!!)
        }

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation;
        dialog.window?.setGravity(Gravity.BOTTOM);
    }

    private fun setFilterFlight(index: Int) {
        val listFilter = listOf<String>("toLower","earlyDeparture","lastDeparture","earlyArrive", "lastArrive")
        val filterMap = mutableMapOf<String,Boolean>()
        for (i in listFilter.indices){
            if(i == index){
                filterMap[listFilter[i]] = true
                break
            }

        }

        flightViewModel.getDataFlight(postSearchFlight, filterMap)

    }

    private fun setHoverFilter(bindingDialogFilter: DialogFilterHasilPenerbanganBinding, index : Int) {
        val listLayoutOption : List<RelativeLayout> = listOf(
            bindingDialogFilter.layoutHargaTermurah, bindingDialogFilter.layoutKeberangkatanAwal, bindingDialogFilter.layoutKeberangkatanAkhir, bindingDialogFilter.layoutKedatanganAwal, bindingDialogFilter.layoutKedatanganAkhir
        )

        val listImageCeklis : List<ImageView> = listOf(
            bindingDialogFilter.icCeklisHargaTermurah, bindingDialogFilter.icCeklisKeberangkatanAwal, bindingDialogFilter.icCeklisKeberangkatanAkhir, bindingDialogFilter.icCeklisKedatanganAwal, bindingDialogFilter.icCeklisKedatanganAkhir
        )

        val listTextView : List<TextView> = listOf(
            bindingDialogFilter.tvHarga, bindingDialogFilter.tvKeberangkatanAwal, bindingDialogFilter.tvKeberangkatanAkhir, bindingDialogFilter.tvKedatanganAwal, bindingDialogFilter.tvKedatanganAkhir
        )

        val listTextViewOption : List<TextView> = listOf(
            bindingDialogFilter.tvTermurah, bindingDialogFilter.tvPalingAwal, bindingDialogFilter.tvPalingAkhir, bindingDialogFilter.tvKedatanganPalingAwal, bindingDialogFilter.tvKedatanganPalingAkhir
        )

        for(i in listLayoutOption.indices){
            if(i == index){
                listLayoutOption[i].setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.DARKBLUE04))
                listTextView[i].setTextColor(Color.WHITE)
                listTextViewOption[i].setTextColor(Color.WHITE)
                listImageCeklis[i].visibility = View.VISIBLE
            }else{
                listLayoutOption[i].setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                listTextView[i].setTextColor(Color.BLACK)
                listTextViewOption[i].setTextColor(ContextCompat.getColor(requireContext(), R.color.DARKBLUE04))
                listImageCeklis[i].visibility = View.GONE
            }
        }

    }

    //masih perlu difixan antara menggunakan tab layout atau recycleview
    //menampilkan list filter date
    private fun setRecycleViewDate() {
        val departureDatePick : LocalDate
        val inputDate : LocalDate
        //terjadi masalah ketika back halaman
        if(statusFlightReturnFlight){
            inputDate = LocalDate.parse(dataSearchFlight.returnDate)
            departureDatePick = LocalDate.parse(dataSearchFlight.departureDate)
            Log.i("INFO DATE RETURN", inputDate.toString())
        }else{
            inputDate = LocalDate.parse(dataSearchFlight.departureDate)
            departureDatePick = LocalDate.parse(dataSearchFlight.departureDate)
            Log.i("INFO DATE DEPARTURE", inputDate.toString())
        }

        var listDate = mutableListOf<LocalDate>()
        var listDay = mutableListOf<String>()

        //kurang membuat kondisi jika return date > departure date
        //karena departure date dinamis tidak mengambil dari search date
        //jika selisih dari pick departure date harus < return date
        //dan list pada filter date departure date <= returnDate
         if(statusFlightReturnFlight){
             listDate = if(inputDate <= departureDatePick){
                 setDate(inputDate,0,6)
             }else{
                 val dateDifference = countDifferenceDate(dataSearchFlight.departureDate,dataSearchFlight.returnDate)
                 if(dateDifference > 3){
                     setDate(inputDate, -3, 3)
                 }else{
                     setDate(inputDate, -dateDifference.toInt(), 3)
                 }

             }
        }else{
            if(dataSearchFlight.returnDate.isEmpty()){
                listDate = if (LocalDate.now() == inputDate){
                    setDate(inputDate,0,6)
                }else{
                    setDate(inputDate, -3, 3)
                }
            }else{
                //permasalahan
                val dateDifference = countDifferenceDate(dataSearchFlight.departureDate,dataSearchFlight.returnDate)
//                Toast.makeText(context, "$dateDifference", Toast.LENGTH_SHORT).show()
                listDate = if(dateDifference <= 7){
                    if (LocalDate.now() == inputDate){
                        if(dateDifference > 6){
                            setDate(inputDate,0,6)
                        }else{
                            setDate(inputDate,0,dateDifference.toInt())
                        }
                    }else{
                        if(dateDifference < 3){
                            setDate(inputDate, -3, dateDifference.toInt())
                        }else{
                            setDate(inputDate, -3, 3)
                        }

                    }
                }else{
                    if (LocalDate.now() == inputDate){
                        setDate(inputDate,0,dateDifference.toInt())
                    }else{
                        setDate(inputDate, -3, dateDifference.toInt())
                    }
                }
            }

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

        if(statusFlightReturnFlight){
            departureDateAdapter.dateDeparture = dataSearchFlight.returnDate
        }else{
            departureDateAdapter.dateDeparture = dataSearchFlight.departureDate
        }

        binding.rvDateFlight.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = departureDateAdapter
        }

        departureDateAdapter.clickItemDate ={
            departureDateAdapter.dateDeparture = it.date
            departureDateAdapter.setListDate(list)
            //change date departure
            //ketika pindah screen maka data departure date tidak tersimpan, sehingga default ke date awal
            if(statusFlightReturnFlight){
                postSearchFlight.departureDate = it.date
                dataSearchFlight.returnDate = it.date
            }else{
                dataSearchFlight.departureDate = it.date
                postSearchFlight.departureDate = it.date
            }

            getAllDataFlight(postSearchFlight)
            Log.d("HASIL_PERUBAHAN_DATE", dataSearchFlight.toString())
            Log.d("HASIL_PERUBAHAN_DATE_POST", postSearchFlight.toString())

            //set visibility layout load
            binding.layoutLoadingData.visibility = View.VISIBLE
            binding.layoutSearchNotFound.visibility = View.GONE


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

    @SuppressLint("SimpleDateFormat")
    private fun countDifferenceDate(inputDateDeparture : String, inputDateReturn : String) : Long{
        val dateFormat = SimpleDateFormat("yyyy-mm-dd")
        val dateDeparture = dateFormat.parse(inputDateDeparture)
        val dateReturn = dateFormat.parse(inputDateReturn)
        val dateDifference = kotlin.math.abs(dateDeparture.time - dateReturn.time)

        return dateDifference / (24 * 60 * 60 * 1000)
    }

}