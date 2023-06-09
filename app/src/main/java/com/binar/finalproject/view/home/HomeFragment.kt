package com.binar.finalproject.view.home

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
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.finalproject.R
import com.binar.finalproject.databinding.*
import com.binar.finalproject.model.Destination
import com.binar.finalproject.model.DestinationFavorite
import com.binar.finalproject.view.adapter.DestinationFavoriteAdapter
import com.binar.finalproject.view.adapter.SearchDestinationAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.timessquare.CalendarPickerView
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding

    //adapter
    private lateinit var destinationFavoriteAdapter: DestinationFavoriteAdapter
    private lateinit var searchDestinationAdapter: SearchDestinationAdapter

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

        setRecycleViewDestinationFavorite()

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
    }

    private fun setSeatClassPassengers() {
        //ketika klik set seat masih tidak menyimpan pilihan sementara di antara 4 pilihan tersebut

        val dialog = BottomSheetDialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.seatclass_dialog_layout)
        val bindingDialog = SeatclassDialogLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialog.root)

        var seatClass = "Economy"
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
            binding.tvSeatClass.text = seatClass
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

    //mengatur jumlah penumpang berdasarkan kategori usia dari dewasa, anak-anak, hingga balita
    private fun setPassengers() {
        //bottom sheet
        val dialog = BottomSheetDialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.passanger_dialog_layout)
        val bindingDialog = PassangerDialogLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialog.root)
        //
        bindingDialog.btnClose.setOnClickListener {
            dialog.dismiss()
        }

        setTotalPassengers(bindingDialog)


        bindingDialog.btnSaveSeatPassenger.setOnClickListener {
            //apakah baby dihitung ???
            //simpan hasil total dan komposisi jumlah penumpang dengan livedata<List> di viewmodel
            val totalPassenger = countPassengers(bindingDialog.tvPassangerAdult,bindingDialog.tvPassangerChild,bindingDialog.tvPassangerBaby)
            val total = "$totalPassenger Penumpang"
            binding.tvPassengers.text = total
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
            if(totalAdult > 1){
                totalAdult -= 1
            }
            bindingDialog.tvPassangerAdult.text = totalAdult.toString()
        }

        bindingDialog.decPassangerChild.setOnClickListener {
            var totalChild = bindingDialog.tvPassangerChild.text.toString().toInt()
            if(totalChild > 1){
                totalChild -= 1
            }
            bindingDialog.tvPassangerChild.text = totalChild.toString()
        }
        bindingDialog.decPassangerBaby.setOnClickListener {
            var totalBaby = bindingDialog.tvPassangerBaby.text.toString().toInt()
            if(totalBaby > 1){
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
            binding.tvDateDeparture.text = bindingDialog.tvDepartureDate.text
            binding.tvDateReturn.text = bindingDialog.tvReturnDate.text
            binding.tvDateReturn.setTextColor(Color.BLACK)
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
        nextYear.add(Calendar.YEAR, 1)

        if(checked){
            bindingDialog.dateFlight.init(startDate,nextYear.time)
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDate(startDate)
        }else{
            bindingDialog.layoutDateReturn.visibility = View.GONE
            bindingDialog.dateFlight.init(startDate,nextYear.time)
                .inMode(CalendarPickerView.SelectionMode.SINGLE)
                .withSelectedDate(startDate)
        }

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
        val listDestination = listOf(
            Destination(1, "Jakarta", "CGK"),
            Destination(2, "Bandung", "BDO"),
            Destination(3, "Jayapura", "DJJ"),
            Destination(4, "Yogyakarta", "YIA"),
            Destination(5, "Denpasar Bali", "DPS"),
            Destination(6, "Banda Aceh", "BTJ"),
            Destination(7,"Lombok", "LOP"),
            Destination(8, "Surabaya", "SUB")
        )
        searchDestinationAdapter = SearchDestinationAdapter(listDestination)

        bindingSearch.rvDestination.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = searchDestinationAdapter
        }

        //ketika item list di klik maka akan set text sesuai dengan action typenya
        searchDestinationAdapter.onClickDestination = {
            val destinationAirport = "${it.airport} (${it.code})"
            if(destinationAirport != binding.tvDeparture.text && destinationAirport != binding.tvArrival.text){
                if(action == "departure"){
                    binding.tvDeparture.text = destinationAirport
                }else{
                    binding.tvArrival.text = destinationAirport
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
    private fun filterDestination(newText: String?, listDestination : List<Destination>) {
        val listSearchDestination = mutableListOf<Destination>()

        for(item in listDestination){
            if(item.airport.toLowerCase().contains(newText!!.toLowerCase())){
                listSearchDestination.add(item)
            }
        }

        if(listSearchDestination.isNotEmpty()){
            searchDestinationAdapter.setListSearchDestination(listSearchDestination)
        }
    }

    //menukar tujuan destinasi dengan tempat keberangkatan
    private fun setChangePosition() {
        val departure = binding.tvDeparture.text
        val arrival = binding.tvArrival.text

        binding.tvDeparture.text = arrival
        binding.tvArrival.text = departure
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