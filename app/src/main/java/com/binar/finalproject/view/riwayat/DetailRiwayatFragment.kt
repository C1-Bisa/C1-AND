package com.binar.finalproject.view.riwayat

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.finalproject.R
import com.binar.finalproject.databinding.FragmentDetailRiwayatBinding
import com.binar.finalproject.local.DataStoreUser
import com.binar.finalproject.model.transactionhistoryperid.request.RequestTransactionId
import com.binar.finalproject.model.transactionhistoryperid.response.Data
import com.binar.finalproject.view.adapter.PassengerAdapter
import com.binar.finalproject.viewmodel.TransactionViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class DetailRiwayatFragment : Fragment() {

    private lateinit var binding : FragmentDetailRiwayatBinding
    private val transactionViewModel : TransactionViewModel by viewModels()
    private lateinit var passengerAdapter : PassengerAdapter
    private lateinit var dataStoreUser: DataStoreUser
    private var token = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailRiwayatBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.visibility = View.GONE

        val getIdTransaction = arguments?.getInt("ID_TRANSACTION")

        //untuk meload data
        binding.layoutLoadingData.visibility = View.VISIBLE

        passengerAdapter = PassengerAdapter(ArrayList())

        dataStoreUser = DataStoreUser(requireContext().applicationContext)

        dataStoreUser.getToken.asLiveData().observe(viewLifecycleOwner){
            if(it != null){
                token = it
                if(getIdTransaction != null){
                    setDetailTransaction(getIdTransaction, token)
                }
            }
        }


        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailRiwayatFragment_to_riwayatFragment)
        }
        binding.btnAction.setOnClickListener {
            val respon = transactionViewModel.responseTransactionById.value?.transaction?.transactionStatus
            val idTrans = transactionViewModel.responseTransactionById.value?.transaction?.id
            if(respon != null){
                if(respon == "Issued"){
                    findNavController().navigate(R.id.action_detailRiwayatFragment_to_homeFragment)
                }else{
                    if(idTrans != null){
                        val bundleIdTrans = Bundle().apply {
                            putInt("ID_TRANSACTION", idTrans)
                        }
                        findNavController().navigate(R.id.action_detailRiwayatFragment_to_paymentFragment, bundleIdTrans)
                    }

                }
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setDetailTransaction(idTrans: Int, token: String) {

        transactionViewModel.getTransactionById(token, RequestTransactionId(idTrans))

        transactionViewModel.responseTransactionById.observe(viewLifecycleOwner){
            if(it != null){
                setDetail(it)

                //untuk meload data
                binding.detaiRiwayatlScrollView.visibility = View.VISIBLE
                binding.layoutLoadingData.visibility = View.GONE

                Log.i("HASIL_DETAIL_RIWAYAT" , it.toString())

                if(it.transaction.transactionStatus == "Issued"){
                    Log.i("HASIL_DETAIL_RIWAYAT" , it.transaction.transactionStatus)
                    binding.btnAction.text = "Beranda"
                    binding.labelRincianRiwayat.setBackgroundResource(R.drawable.background_issued_riwayat)
                    binding.tvStatus.text = it.transaction.transactionStatus
                }else{
                    binding.btnAction.text = "Lanjut Pembayaran"
                    Log.i("HASIL_DETAIL_RIWAYAT" , it.transaction.transactionStatus)
                }
            }else{
                binding.layoutLoadingData.visibility = View.GONE
                binding.detaiRiwayatlScrollView.visibility = View.VISIBLE

            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setDetail(item: Data) {
        binding.apply {
            tvBookingCode.text = item.transaction.transactionCode
            tvTimeDeparture.text = timeFormate(item.departure.flight.departureTime)
            tvDateDeparture.text = setDate(item.departure.flight.departureDate)
            tvDepartureAirport.text = item.departure.flight.airportFrom.airportLocation
            tvAirlineAndClass.text = item.departure.flight.airline.airlineName
            tvCodeAirline.text = item.departure.flight.airline.airlineCode

            rvPassengerDepartureTrip.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = passengerAdapter
            }

            passengerAdapter.setListPassAdapter(item.transaction.passengers)

            tvTimeArrive.text = timeFormate(item.departure.flight.arrivalTime)
            tvDateArrive.text = setDate(item.departure.flight.arrivalDate)
            tvArriveAirport.text = item.departure.flight.airportTo.airportLocation

            tvTotalPrice.text = "IDR ${convertToCurrencyIDR(item.price.totalPrice)}"
            tvTotalTax.text = "IDR ${convertToCurrencyIDR(item.price.tax)}"


        }

        if(item.passenger.adult != 0 || item.passenger.child != 0 || item.passenger.baby != 0){
            if(item.passenger.adult != 0){
                binding.tvNumAdult.text = "${item.passenger.adult} Adult"
                if(item.arrival?.flight != null){
                    binding.tvTotalPriceAdult.text = convertToCurrencyIDR(item.price.departure * item.passenger.adult + item.price.arrival * item.passenger.adult)
                }else{
                    binding.tvTotalPriceAdult.text = convertToCurrencyIDR(item.price.departure * item.passenger.adult)
                }
            }else{
                binding.layoutNumAdult.visibility = View.GONE
            }


            if (item.passenger.child != 0){
                binding.tvNumChild.text = "${item.passenger.child} Child"
                if(item.arrival?.flight != null){
                    binding.tvTotalPriceChild.text = convertToCurrencyIDR(item.price.departure * item.passenger.child + item.price.arrival * item.passenger.child)
                }else{
                    binding.tvTotalPriceChild.text = convertToCurrencyIDR(item.price.departure * item.passenger.child)
                }
            }else{
                binding.layoutNumChild.visibility = View.GONE
            }

            if (item.passenger.baby != 0){
                binding.tvNumBaby.text = "${item.passenger.baby} Baby"
                if(item.arrival?.flight != null){
                    binding.tvTotalPriceBaby.text = "IDR 0"
                }else{
                    binding.tvTotalPriceBaby.text = "IDR 0"
                }
            }else{
                binding.layoutNumBaby.visibility = View.GONE
            }
        }else{
            binding.layoutNumAdult.visibility = View.GONE
            binding.layoutNumChild.visibility = View.GONE
            binding.layoutNumBaby.visibility = View.GONE
        }


        if(item.arrival?.flight != null){
            Log.d("ARRIVAL ", "ARRIVAL")
            binding.apply {
                layoutInfoScheduleReturn.visibility = View.VISIBLE
                tvBookingCodeReturn.text = item.transaction.transactionCode
                tvTimeDepartureReturnTrip.text = timeFormate(item.arrival.flight.departureTime)
                tvDateDepartureReturnTrip.text = setDate(item.arrival.flight.departureDate)
                tvDepartureAirportReturnTrip.text = item.arrival.flight.airportFrom.airportLocation
                tvAirlineAndClassReturn.text = item.arrival.flight.airline.airlineName
                tvCodeAirlineReturn.text = item.arrival.flight.airline.airlineCode

                rvPassengerReturnTrip.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    adapter = passengerAdapter
                }

                passengerAdapter.setListPassAdapter(item.transaction.passengers)

                tvTimeArriveReturnTrip.text = timeFormate(item.arrival.flight.arrivalTime)
                tvDateArriveReturnTrip.text = setDate(item.arrival.flight.arrivalDate)
                tvArriveAirportReturnTrip.text = item.arrival.flight.airportTo.airportLocation
            }
        }
    }

    private fun timeFormate(time : String) : String{
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        val parsedTimeLocal = LocalTime.parse(time, timeFormatter)
        return parsedTimeLocal.format(DateTimeFormatter.ofPattern("HH:mm"))
    }

    private fun convertToCurrencyIDR(price : Int) : String{
        val currencyFormatIDR = DecimalFormat("#,###", DecimalFormatSymbols.getInstance(Locale("id", "ID")))
        return currencyFormatIDR.format(price)

    }

    private fun setDate(date : String) : String{
        val zonedDateTimeParse = ZonedDateTime.parse(date)
        val localDateTimeAsia = zonedDateTimeParse.withZoneSameInstant(ZoneId.of("Asia/Jakarta")).toLocalDateTime()
        val dateFormatterId = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("id"))
        return localDateTimeAsia.format(dateFormatterId)
    }

}