package com.binar.finalproject.view.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.binar.finalproject.R
import com.binar.finalproject.databinding.DialogAlertCheckoutNonLoginBinding
import com.binar.finalproject.databinding.DialogTicketSoldOutBinding
import com.binar.finalproject.databinding.FragmentDetailPenerbanganBinding
import com.binar.finalproject.databinding.PassangerDialogLayoutBinding
import com.binar.finalproject.databinding.SeatclassDialogLayoutBinding
import com.binar.finalproject.local.DataStoreUser
import com.binar.finalproject.model.searchflight.Flight
import com.binar.finalproject.model.searchflight.FlightTicketOneTrip
import com.binar.finalproject.model.searchflight.FlightTicketRoundTrip
import com.binar.finalproject.model.searchflight.SearchFlight
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DetailPenerbanganFragment : Fragment() {

    private lateinit var binding : FragmentDetailPenerbanganBinding
    private lateinit var flight: Flight
    private lateinit var dataStoreUser : DataStoreUser
    private var flightTicketOneTrip = FlightTicketOneTrip()
    private var flightTicketRoundTrip = FlightTicketRoundTrip()
    private var dataSearchFlight = SearchFlight()

    //status apakah flight departure atau flight return

    private var statusPickFlightReturn : Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailPenerbanganBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataStoreUser = DataStoreUser(requireContext().applicationContext)

        val getBundleDetailFlight = arguments?.getSerializable("DATA_FLIGHT")
        val getDataSearch = arguments?.getSerializable("DATA_SEARCH")
        val getPassenger = arguments?.getString("DATA_PASSENGER")
        val getSeatClass = arguments?.getString("DATA_SEATCLASS")
        val getPickFligtReturn = arguments?.getBoolean("STATUS_PICK_FLIGHT_RETURN")
        val getDataRoundTrip = arguments?.getSerializable("DATA_FLIGHT_ROUND_TRIP")
        val getListSeatPassenger = arguments?.getIntArray("DATA_LIST_NUM_SEAT")

        if(getBundleDetailFlight != null){
            setDataFlight(getBundleDetailFlight as Flight)
            dataSearchFlight = getDataSearch as SearchFlight
            flight = getBundleDetailFlight
            Log.i("DATA_DETAIL_FLIGHT", getBundleDetailFlight.toString())
        }

        if(getPickFligtReturn != null && getDataRoundTrip != null){
            statusPickFlightReturn = getPickFligtReturn
            flightTicketRoundTrip = getDataRoundTrip as FlightTicketRoundTrip
        }

        binding.btnSelectFlight.setOnClickListener {
//            jika ticket habis show dialog
//            showDialogTecketSoldOut()
            if(flight.id.toString().isNotEmpty() && dataSearchFlight.returnDate.isEmpty() && !statusPickFlightReturn){
                flightTicketOneTrip.flightIdDeparture = flight.id
                Log.i("ID_FLIGHT_DEPARTURE", flightTicketOneTrip.flightIdDeparture.toString())

                val putBundleDataFlight = Bundle().apply {
                    putIntArray("DATA_LIST_NUM_SEAT", getListSeatPassenger)
                    putBoolean("TYPE_TRIP_ROUNDTRIP", false)
                    putSerializable("DATA_FLIGHT_ONE_TRIP", flightTicketOneTrip)
                }
                if(dataStoreUser.isAlreadyLogin()){
                    findNavController().navigate(R.id.action_detailPenerbanganFragment_to_biodataPemesanFragment, putBundleDataFlight)
                }else{
                    showDialogToLogin()
                }

            }else{
                if(!statusPickFlightReturn){
                    flightTicketRoundTrip.flightIdDeparture = flight.id
                    val putBundleFlight = Bundle().apply {
                        putBoolean("STATUS_PICK_FLIGHT_RETURN", true)
                        putSerializable("DATA_SEARCH", dataSearchFlight)
                        putString("DATA_PASSENGER", getPassenger)
                        putString("DATA_SEATCLASS", getSeatClass)
                        putSerializable("DATA_FLIGHT_ROUND_TRIP", flightTicketRoundTrip)
                        putIntArray("DATA_LIST_NUM_SEAT", getListSeatPassenger)
                    }
                    Log.i("ID_FLIGHT_DEPARTURE", flightTicketRoundTrip.toString())

                    findNavController().navigate(R.id.action_detailPenerbanganFragment_to_hasilPencarianFragment, putBundleFlight)
                }else{
                    flightTicketRoundTrip.flightIdReturn = flight.id
                    Log.i("ID_FLIGHT_DEPARTURE", flightTicketRoundTrip.toString())
                    val putBundleDataFlight = Bundle().apply {
                        putIntArray("DATA_LIST_NUM_SEAT", getListSeatPassenger)
                        putBoolean("TYPE_TRIP_ROUNDTRIP", true)
                        putSerializable("DATA_FLIGHT_ROUND_TRIP", flightTicketRoundTrip)
                    }
                    //jika belum login maka akan show dialog login
                    if(dataStoreUser.isAlreadyLogin()){
                        findNavController().navigate(R.id.action_detailPenerbanganFragment_to_biodataPemesanFragment, putBundleDataFlight)
                    }else{
                        showDialogToLogin()
                    }
                  }
            }

        }

        //button back
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }


    }

    private fun showDialogToLogin() {
        val dialog = BottomSheetDialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_alert_checkout_non_login)
        val bindingDialog = DialogAlertCheckoutNonLoginBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialog.root)

        bindingDialog.btnClose.setOnClickListener {
            dialog.dismiss()
        }

        bindingDialog.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_detailPenerbanganFragment_to_loginFragment)
            dialog.dismiss()
        }

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation;
        dialog.window?.setGravity(Gravity.BOTTOM);
    }

    @SuppressLint("SetTextI18n")
    private fun setDataFlight(data: Flight) {
        //nanti diganti menggunakan data binding
        binding.tvFlightDestination.text = "${data.from} -> ${data.to}"
        binding.tvFlightTime.text = "(${reformatDuration(data.duration.toString())})"
        binding.tvTimeDeparture.text = timeFormate(data.departureTime)
        binding.tvDateDeparture.text = setDate(data.departureDate)
        binding.tvDepartureAirport.text = data.airportFrom
        binding.tvAirline.text = data.airline
        binding.tvAirlineCode.text = data.airlaneCode
        binding.tvInformation.text = setDescription(data.description)
        binding.tvTimeArrive.text = timeFormate(data.arrivalTime)
        binding.tvDateArrive.text = setDate(data.arrivalDate)
        binding.tvArriveAirport.text = data.airportTo
        binding.tvPriceTicket.text = "IDR ${convertToCurrencyIDR(data.price)}/PAX"
    }

    private fun setDescription(des : String) : String{
        return des.replace("kg", "kg\n")
    }
    private fun convertToCurrencyIDR(price : Int) : String{
        val currencyFormatIDR = DecimalFormat("#,###", DecimalFormatSymbols.getInstance(Locale("id", "ID")))
        return currencyFormatIDR.format(price)

    }

    //untuk mengubah tima format (hh:mm:ss) ke (hh:mm)
    private fun timeFormate(time : String) : String{
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        val parsedTimeLocal = LocalTime.parse(time, timeFormatter)
        return parsedTimeLocal.format(DateTimeFormatter.ofPattern("HH:mm"))
    }

    private fun setDate(date : String) : String{
        val zonedDateTimeParse = ZonedDateTime.parse(date)
        val localDateTimeAsia = zonedDateTimeParse.withZoneSameInstant(ZoneId.of("Asia/Jakarta")).toLocalDateTime()
        val dateFormatterId = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("id"))
        return localDateTimeAsia.format(dateFormatterId)
    }

    private fun reformatDuration(duration: String): String {
        val text = duration.toCharArray()
            .filter { it != '9' }
            .toCharArray()
        return "${text[0]}h ${text[1]}m"
    }



    private fun showDialogTecketSoldOut() {
        val dialog = BottomSheetDialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_ticket_sold_out)
        val bindingDialog = DialogTicketSoldOutBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialog.root)

        bindingDialog.btnClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation;
        dialog.window?.setGravity(Gravity.BOTTOM);
    }

}