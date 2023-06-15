package com.binar.finalproject.view.home

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.finalproject.R
import com.binar.finalproject.databinding.*
import com.binar.finalproject.databinding.FragmentHomeBinding
import com.binar.finalproject.model.Destination
import com.binar.finalproject.model.DestinationFavorite
import com.binar.finalproject.model.airport.Airport
import com.binar.finalproject.model.searchflight.SearchFlight
import com.binar.finalproject.view.adapter.DestinationFavoriteAdapter
import com.binar.finalproject.view.adapter.SearchDestinationAdapter
import com.binar.finalproject.viewmodel.AirportViewModel
import com.binar.finalproject.viewmodel.FlightSearchViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.timessquare.CalendarPickerView
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding

    //adapter
    private lateinit var destinationFavoriteAdapter: DestinationFavoriteAdapter
    private lateinit var searchDestinationAdapter: SearchDestinationAdapter

    //viewmodel
    private val airportViewModel : AirportViewModel by viewModels()
    private val flightSearchViewModel : FlightSearchViewModel by viewModels()

    //data class for search flight
    private lateinit var dataSearch : SearchFlight

    //from and to
    private lateinit var from : String
    private lateinit var to : String




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //untuk menampilkan kembalii bottom navigation
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.visibility = View.VISIBLE

        setRecycleViewDestinationFavorite()

        //initial
        if(flightSearchViewModel.searchFrom.value != null && flightSearchViewModel.searchTo.value != null){
            from = flightSearchViewModel.searchFrom.value.toString()
            to = flightSearchViewModel.searchTo.value.toString()
        }else{
            from = "Jakarta"
            to = "Bali"
        }


        //tukar destination dengan tempat keberangkatan
        binding.btnChange.setOnClickListener {
            val animBtn = AnimationUtils.loadAnimation(context, R.anim.anim_rotate)
            binding.btnChange.startAnimation(animBtn)
            setChangePosition()
        }

        //pencarian asal keberangkatan
        binding.setDeparture.setOnClickListener {
            setLocationFlight("departure")
        }

        //pencarian tujuan penerbangan
        binding.setArrival.setOnClickListener {
            setLocationFlight("arrival")
        }

        //set option pulang pergi atau pergi saja
        binding.optionFlight.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                binding.setDateReturn.visibility = View.VISIBLE

                if(!dateReturnBeforeDateDeparture(flightSearchViewModel.dateDeparture.value.toString(), flightSearchViewModel.dateReturn.value.toString())){
                    //set date return trip more than date departure date
                    val dateDeparture = flightSearchViewModel.dateDeparture.value
                    val dateReturnTrip = setDateReturn(dateDeparture.toString())
                    flightSearchViewModel.setDateReturn(setDateReturn(dateReturnTrip))
                    flightSearchViewModel.setSearchReturnDate(changeFormatDateEn(setDateReturn(dateReturnTrip)))
                }

                Toast.makeText(context, "${flightSearchViewModel.dateDeparture.value}", Toast.LENGTH_SHORT).show()

            }else{
                binding.setDateReturn.visibility = View.GONE
            }
        }

        //set date keberangkatan
        binding.setDateDeparture.setOnClickListener {
            setDateFlight(binding.optionFlight.isChecked)
        }

        //set date pulang
        binding.setDateReturn.setOnClickListener {
            setDateFlight(binding.optionFlight.isChecked)
        }

        //set jumlah penumpang
        binding.setPassangers.setOnClickListener {
            setPassengers()
        }

        //set seat class
        binding.setSeatClass.setOnClickListener {
            setSeatClassPassengers()
        }

        //klik button pencarian penerbangan
        binding.btnSearchFlight.setOnClickListener {
            searchFlight()
            Log.d("DATA_PASSENGER", flightSearchViewModel.dataPassenger.value.toString())

        }
        //belum bisa

        showDataSearchFlight()

    }

    private fun searchFlight() {

        val from : String = if(flightSearchViewModel.searchFrom.value != null){
            Log.d("HASIL_FROM", flightSearchViewModel.searchFrom.value.toString())
            flightSearchViewModel.searchFrom.value.toString()
        }else{
            "Jakarta"
        }

        val to : String = if(flightSearchViewModel.searchTo.value != null){
            flightSearchViewModel.searchTo.value.toString()
        }else{
            "Bali"
        }

        val dateDeparture = if(flightSearchViewModel.searchDateDeparture.value != null){
            flightSearchViewModel.searchDateDeparture.value.toString()
        }else{
            changeFormatDateEn(binding.tvDateDeparture.text.toString())
        }

        val dateReturn = if(binding.optionFlight.isChecked){
            if (flightSearchViewModel.searchDateReturn.value != null){
                flightSearchViewModel.searchDateReturn.value.toString()
            }else{
                changeFormatDateEn(binding.tvDateReturn.text.toString())
            }

        }else{
            ""
        }



        val dataBundle = Bundle().apply {
            putSerializable("DATA_SEARCH", SearchFlight(
                dateDeparture,
                "00:00",
                from,
                dateReturn,
                to
            ))
            putString("DATA_PASSENGER", binding.tvPassengers.text.toString())
            putString("DATA_SEATCLASS", binding.tvSeatClass.text.toString())
        }
        findNavController().navigate(R.id.action_homeFragment_to_hasilPencarianFragment, dataBundle)
    }

    //menampilkan data dari viewmodel flightsearch
    private fun showDataSearchFlight() {

        flightSearchViewModel.seatClass.observe(viewLifecycleOwner){
            binding.tvSeatClass.text = it
        }

        flightSearchViewModel.countPassenger.observe(viewLifecycleOwner){
            binding.tvPassengers.text = it
        }

        flightSearchViewModel.from.observe(viewLifecycleOwner){
            binding.tvDeparture.text = it
        }

        flightSearchViewModel.to.observe(viewLifecycleOwner){
            binding.tvArrival.text = it
        }

        flightSearchViewModel.dateDeparture.observe(viewLifecycleOwner){
            binding.tvDateDeparture.text = it
        }

        flightSearchViewModel.dateReturn.observe(viewLifecycleOwner){
            binding.tvDateReturn.text = it

        }
    }

    //ubah menjadi en date
    private fun changeFormatDateEn(input : String) : String{

        val inputFormatterDate = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("id", "ID"))
        val outputFormatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(input, inputFormatterDate)
        return date.format(outputFormatterDate)
    }

    //CHOICE SEATCLASS
    private fun setSeatClassPassengers() {
        //ketika klik set seat masih tidak menyimpan pilihan sementara di antara 4 pilihan tersebut

        val dialog = BottomSheetDialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.seatclass_dialog_layout)
        val bindingDialog = SeatclassDialogLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialog.root)

        //tambahan
        var seatClass : String

        if(flightSearchViewModel.seatClass.value != null){

            seatClass = flightSearchViewModel.seatClass.value.toString()

            when(seatClass){
                "Economy" -> setChoiceClass(0, bindingDialog)
                "Premium Economy" -> setChoiceClass(1, bindingDialog)
                "Business" -> setChoiceClass(2, bindingDialog)
                else-> setChoiceClass(3, bindingDialog)
            }

        }else{
            seatClass = "Business"
            flightSearchViewModel.setSeatClass(seatClass)
            setChoiceClass(2, bindingDialog)
        }

        //
        bindingDialog.btnClose.setOnClickListener {
            dialog.dismiss()
        }
        bindingDialog.layoutEconomy.setOnClickListener {
            setChoiceClass(0, bindingDialog)
            seatClass = "Economy"
        }

        bindingDialog.layoutPremiumEconomy.setOnClickListener {
            setChoiceClass(1, bindingDialog)
            seatClass = "Premium Economy"
        }
        bindingDialog.layoutBusiness.setOnClickListener {
            setChoiceClass(2, bindingDialog)
            seatClass = "Business"
        }
        bindingDialog.layoutFirstClass.setOnClickListener {
            setChoiceClass(3, bindingDialog)
            seatClass = "First Class"
        }

        bindingDialog.btnSaveClass.setOnClickListener {
            flightSearchViewModel.setSeatClass(seatClass)
            dialog.dismiss()
        }
        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation;
        dialog.window?.setGravity(Gravity.BOTTOM);
    }
    private fun setChoiceClass(i: Int, bindingDialog : SeatclassDialogLayoutBinding)  {

        val listLayout : List<RelativeLayout> = listOf(
            bindingDialog.layoutEconomy, bindingDialog.layoutPremiumEconomy, bindingDialog.layoutBusiness, bindingDialog.layoutFirstClass
        )
        val listTvClass : List<TextView> = listOf(
            bindingDialog.tvEconomy, bindingDialog.tvPremiumEconomy, bindingDialog.tvBusiness, bindingDialog.tvFirstClass
        )
        val listTvPrice : List<TextView> = listOf(
            bindingDialog.tvPriceEconomy, bindingDialog.tvPricePremiumEconomy, bindingDialog.tvPriceBusiness, bindingDialog.tvPriceFristClass
        )
        val listImgCeklis : List<ImageView> = listOf(
            bindingDialog.icCeklisEconomy, bindingDialog.icCeklisPremiumEconomy, bindingDialog.icCeklisBusiness, bindingDialog.icCeklisFirstClass
        )

        for (index in listLayout.indices){
            if(i == index){
                listLayout[index].setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.DARKBLUE04))
                listTvClass[index].setTextColor(Color.WHITE)
                listTvPrice[index].setTextColor(Color.WHITE)
                listImgCeklis[index].visibility = View.VISIBLE
            }else{
                listLayout[index].setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                listTvClass[index].setTextColor(Color.BLACK)
                listTvPrice[index].setTextColor(ContextCompat.getColor(requireContext(), R.color.DARKBLUE04))
                listImgCeklis[index].visibility = View.GONE
            }
        }
    }

    //SET COUNT PASSENGERS
    //mengatur jumlah penumpang berdasarkan kategori usia dari dewasa, anak-anak, hingga balita
    private fun setPassengers() {
        //bottom sheet
        val dialog = BottomSheetDialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.passanger_dialog_layout)
        val bindingDialog = PassangerDialogLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialog.root)

        //deklarsi dari view model
        //set konfigurasi jumlah penumpang yang di dapatkan dari viewmodel
        if(flightSearchViewModel.dataPassenger.value != null){
            bindingDialog.tvPassangerAdult.text = flightSearchViewModel.dataPassenger.value!![0].toString()
            bindingDialog.tvPassangerChild.text = flightSearchViewModel.dataPassenger.value!![1].toString()
            bindingDialog.tvPassangerBaby.text = flightSearchViewModel.dataPassenger.value!![2].toString()
        }
        //
        bindingDialog.btnClose.setOnClickListener {
            dialog.dismiss()
        }

        setTotalPassengers(bindingDialog)


        bindingDialog.btnSaveSeatPassenger.setOnClickListener {
            //apakah baby dihitung ???
            //simpan hasil total dan komposisi jumlah penumpang dengan livedata<List> di viewmodel
            val totalPassenger = countPassengers(bindingDialog.tvPassangerAdult,bindingDialog.tvPassangerChild,bindingDialog.tvPassangerBaby)

            if(totalPassenger >= 1){

                //set data jumlah passenger ke dalam viewmodel
                flightSearchViewModel.setDataPassenger(0, bindingDialog.tvPassangerAdult.text.toString().toInt())
                flightSearchViewModel.setDataPassenger(1, bindingDialog.tvPassangerChild.text.toString().toInt())
                flightSearchViewModel.setDataPassenger(2, bindingDialog.tvPassangerBaby.text.toString().toInt())

                val total = "$totalPassenger Penumpang"
                flightSearchViewModel.setCountPassenger(total)
//                binding.tvPassengers.text = total
            }

            dialog.dismiss()
        }
        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation;
        dialog.window?.setGravity(Gravity.BOTTOM);
    }

    private fun setTotalPassengers(bindingDialog: PassangerDialogLayoutBinding) {
        bindingDialog.addPassangerAdult.setOnClickListener {
            val totalAdult = bindingDialog.tvPassangerAdult.text.toString().toInt() + 1
            bindingDialog.tvPassangerAdult.text = totalAdult.toString()
        }

        bindingDialog.addPassangerChild.setOnClickListener {
            val totalChild = bindingDialog.tvPassangerChild.text.toString().toInt() + 1
            bindingDialog.tvPassangerChild.text = totalChild.toString()
        }
        bindingDialog.addPassangerBaby.setOnClickListener {
            val totalBaby = bindingDialog.tvPassangerBaby.text.toString().toInt() + 1
            bindingDialog.tvPassangerBaby.text = totalBaby.toString()
        }

        bindingDialog.decPassangerAdult.setOnClickListener {
            var totalAdult = bindingDialog.tvPassangerAdult.text.toString().toInt()
            if(totalAdult >= 1){
                totalAdult -= 1
            }
            bindingDialog.tvPassangerAdult.text = totalAdult.toString()
        }

        bindingDialog.decPassangerChild.setOnClickListener {
            var totalChild = bindingDialog.tvPassangerChild.text.toString().toInt()
            if(totalChild >= 1){
                totalChild -= 1
            }
            bindingDialog.tvPassangerChild.text = totalChild.toString()
        }
        bindingDialog.decPassangerBaby.setOnClickListener {
            var totalBaby = bindingDialog.tvPassangerBaby.text.toString().toInt()
            if(totalBaby >= 1){
                totalBaby -= 1
            }
            bindingDialog.tvPassangerBaby.text = totalBaby.toString()
        }


    }

    private fun countPassengers(vararg passangers : TextView) : Int{
        var count = 0
        for(item in passangers){
            count += item.text.toString().toInt()
        }
        return count
    }


    //SET DATE FLIGHT AND RETURN DATE
    //menampilkan tanggal keberangkatan dan kepulangan(optional)
    private fun setDateFlight(checked : Boolean) {
        //nanti dikasih kondisi apakah tanggal pulang pergi atau single flight
        //ketika saat awal memasukkan date hanya keberangkatan trs mengaktifkan pulang pergi, date kepulangan terjadi kesalahan yaitu menampilkan hari

        val dialog = BottomSheetDialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.date_dialog_layout)
        val bindingDialog = DateDialogLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialog.root)

        dateFlight(bindingDialog, checked)


        bindingDialog.btnClose.setOnClickListener {
            dialog.dismiss()
        }
        bindingDialog.btnSaveDate.setOnClickListener {
            if(checked){
                flightSearchViewModel.setDateDeparture(bindingDialog.tvDepartureDate.text.toString())
                flightSearchViewModel.setDateReturn(bindingDialog.tvReturnDate.text.toString())

                flightSearchViewModel.setSearchDateDeparture(changeFormatDateEn(bindingDialog.tvDepartureDate.text.toString()))
                flightSearchViewModel.setSearchReturnDate(changeFormatDateEn(bindingDialog.tvReturnDate.text.toString()))

                binding.tvDateReturn.setTextColor(Color.BLACK)
            }else{
                flightSearchViewModel.setDateDeparture(bindingDialog.tvDepartureDate.text.toString())
                flightSearchViewModel.setSearchDateDeparture(changeFormatDateEn(bindingDialog.tvDepartureDate.text.toString()))

            }
            dialog.dismiss()
        }
        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation;
        dialog.window?.setGravity(Gravity.BOTTOM);
    }

    private fun dateFlight(bindingDialog: DateDialogLayoutBinding, checked: Boolean) {
        val startDate = Date()
        val endDate = Date()
        val nextYear = Calendar.getInstance()
        //initial date return and departure
        val dateDeparture = flightSearchViewModel.dateDeparture.value.toString()
        val dateReturn = flightSearchViewModel.dateReturn.value.toString()
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
        var dateDepartureFormat : Date = Date()
        var dateReturnFormat : Date = Date()
        if(dateDeparture.isNotEmpty() && dateReturn.isNotEmpty()){
            dateDepartureFormat = dateFormat.parse(dateDeparture) as Date
            dateReturnFormat = dateFormat.parse(dateReturn) as Date
        }

        nextYear.add(Calendar.YEAR, 1)

        if(checked){
            //kondisi untuk mempermudah pemilihan tanggal penerbangan
            if(dateDeparture.isNotEmpty() && dateReturn.isNotEmpty()){
                bindingDialog.dateFlight.init(startDate,nextYear.time)
                    .inMode(CalendarPickerView.SelectionMode.RANGE)
                    .withSelectedDates(listOf(dateDepartureFormat, dateReturnFormat))
                bindingDialog.dateFlight.scrollToDate(dateDepartureFormat)
            }else{
                bindingDialog.dateFlight.init(startDate,nextYear.time)
                    .inMode(CalendarPickerView.SelectionMode.RANGE)
                    .withSelectedDate(startDate)
            }


        }else{
            if(dateDeparture.isNotEmpty()){
                bindingDialog.layoutDateReturn.visibility = View.GONE
                bindingDialog.dateFlight.init(startDate,nextYear.time)
                    .inMode(CalendarPickerView.SelectionMode.SINGLE)
                    .withSelectedDate(dateDepartureFormat)
                bindingDialog.dateFlight.scrollToDate(dateDepartureFormat)
            }else{
                bindingDialog.layoutDateReturn.visibility = View.GONE
                bindingDialog.dateFlight.init(startDate,nextYear.time)
                    .inMode(CalendarPickerView.SelectionMode.SINGLE)
                    .withSelectedDate(startDate)
            }
        }

        //kurang deklarasi hari
        bindingDialog.dateFlight.setOnDateSelectedListener(object : CalendarPickerView.OnDateSelectedListener {
            override fun onDateSelected(date: Date) {
                val selectedDates = bindingDialog.dateFlight.selectedDates
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                if (selectedDates.size >= 2) {
                    val dateDeparture = selectedDates[0]
                    val dateReturn = selectedDates[selectedDates.size - 1]
                    bindingDialog.tvDepartureDate.text = convertDateFormatID(dateFormat.format(dateDeparture).toString())
                    bindingDialog.tvReturnDate.text = convertDateFormatID(dateFormat.format(dateReturn).toString())
                }else{
                    bindingDialog.tvDepartureDate.text = convertDateFormatID(dateFormat.format(date).toString())
                }
            }

            override fun onDateUnselected(date: Date) {
                // Tindakan saat tanggal yang dipilih dibatalkan (tidak digunakan dalam mode selection range)
            }
        })

    }
    fun convertDateFormatID(dateString: String): String {
        val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formatDateID = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
        val date: Date = inputFormat.parse(dateString) as Date
        return formatDateID.format(date)
    }

    //set date pada return trip sehingga date return tidak < dari date departure
    private fun setDateReturn(date : String) : String{
        val dateReturnFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("id", "ID"))
        val dateDeparture = flightSearchViewModel.dateDeparture.value
        val parseDateDeparture = LocalDate.parse(dateDeparture, dateReturnFormat)

        return parseDateDeparture.plusDays(1).format(dateReturnFormat)
    }

    //set kondisi apakah date departure trip sesudah date return trip

    private fun dateReturnBeforeDateDeparture(dateDepart : String, dateRet : String) : Boolean{
        val formatDate = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("id", "ID"))
        val dateDeparture = LocalDate.parse(dateDepart, formatDate)
        val dateReturn = LocalDate.parse(dateRet, formatDate)

        // Membandingkan tanggal apakah tanggal departure sebelum tanggal return
        return dateDeparture.isBefore(dateReturn)
    }

    //SET LOCATION FLIGHT AND DESTINATION
    //menampilkan dialog untuk pencarian destinasi
    private fun setLocationFlight(action : String) {

        val dialogSearchDestination = Dialog(requireContext())

        dialogSearchDestination.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogSearchDestination.setContentView(R.layout.search_dialog_layout)
        val bindingSearch = SearchDialogLayoutBinding.inflate(layoutInflater)
        dialogSearchDestination.setContentView(bindingSearch.root)


        //menutup dialog
        bindingSearch.btnClose.setOnClickListener {
            dialogSearchDestination.dismiss()
        }

        dialogSearchDestination.show()
        dialogSearchDestination.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialogSearchDestination.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialogSearchDestination.window?.attributes?.windowAnimations = R.style.DialogAnimation;
        dialogSearchDestination.window?.setGravity(Gravity.BOTTOM);

        //testing adapter searchdestination
        var listDestination = mutableListOf<Airport>()
        searchDestinationAdapter = SearchDestinationAdapter(ArrayList())

        bindingSearch.rvDestination.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = searchDestinationAdapter
        }

        //get data from api airport
        airportViewModel.getDataAirport()

        airportViewModel.dataAirport.observe(viewLifecycleOwner){
            listDestination.clear()
            if (it.isNotEmpty()){
                searchDestinationAdapter.setListSearchDestination(it)
                listDestination.addAll(it)
            }
        }

        //ketika item list di klik maka akan set text sesuai dengan action typenya
        //
        searchDestinationAdapter.onClickDestination = {
            val destinationAirport = "${it.airportLocation} (${it.airportCode})"
            if(destinationAirport != binding.tvDeparture.text && destinationAirport != binding.tvArrival.text){
                if(action == "departure"){
                    from = it.airportLocation

                    flightSearchViewModel.setFrom(destinationAirport)
                    flightSearchViewModel.setSearchFrom(it.airportLocation)

                }else{
                    to = it.airportLocation

                    flightSearchViewModel.setTo(destinationAirport)
                    flightSearchViewModel.setSearchTo(it.airportLocation)
                }
            }
            dialogSearchDestination.dismiss()
        }

        //search query listener

        bindingSearch.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterDestination(newText, listDestination)
                return true
            }

        })

    }

    //filtering input search destination
    private fun filterDestination(newText: String?, listDestination : List<Airport>) {
        val listSearchDestination = mutableListOf<Airport>()

        for(item in listDestination){
            if(item.airportLocation.toLowerCase().contains(newText!!.toLowerCase())){
                listSearchDestination.add(item)
            }
        }

        if(listSearchDestination.isNotEmpty()){
            searchDestinationAdapter.setListSearchDestination(listSearchDestination)
        }
    }

    //
    //menukar tujuan destinasi dengan tempat keberangkatan
    private fun setChangePosition() {
        val departure = binding.tvDeparture.text
        val arrival = binding.tvArrival.text

        val fromTemporary = this.to
        val toTemporary = this.from

//        binding.tvDeparture.text = arrival
//        binding.tvArrival.text = departure

        flightSearchViewModel.setFrom(arrival as String)
        flightSearchViewModel.setTo(departure as String)


        //local var
        this.from = fromTemporary
        this.to = toTemporary
        Log.d("HASIL_CHANGE", "$from $to")
        //update viewmodel
        //belum benar
        flightSearchViewModel.setSearchFrom(fromTemporary)
        flightSearchViewModel.setSearchTo(toTemporary)


    }

    //menampilkan list destinasi favorit
    private fun setRecycleViewDestinationFavorite() {
        //testing adapter
        val listDataDestinationFavorite = listOf(
            DestinationFavorite(R.drawable.contoh_destination_favorite, "Jakarta -> Surabaya", "Lion Air", "23 - 30 Mei 2023", "IDR 2.000.000"),
            DestinationFavorite(R.drawable.contoh_destination_favorite, "Bali -> Surabaya", "Batik Air", "25 - 30 Mei 2023", "IDR 1.000.000"),
            DestinationFavorite(R.drawable.contoh_destination_favorite, "Jakarta -> Jayapura", "Citilink", "29 - 30 Mei 2023", "IDR 4.000.000"),
            DestinationFavorite(R.drawable.contoh_destination_favorite, "Jakarta -> Makassar", "Superjet", "20 - 30 Mei 2023", "IDR 3.000.000"),
            DestinationFavorite(R.drawable.contoh_destination_favorite, "Semarang -> Surabaya", "Lion Air", "23 - 30 Mei 2023", "IDR 1.500.000")

        )

        destinationFavoriteAdapter = DestinationFavoriteAdapter(listDataDestinationFavorite)

        binding.rvFavoriteDestination.apply{
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = destinationFavoriteAdapter
        }
    }

}