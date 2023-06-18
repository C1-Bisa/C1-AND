package com.binar.finalproject.view.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.binar.finalproject.R
import com.binar.finalproject.databinding.DialogTicketSoldOutBinding
import com.binar.finalproject.databinding.FragmentDetailPenerbanganBinding
import com.binar.finalproject.databinding.SeatclassDialogLayoutBinding
import com.binar.finalproject.model.searchflight.Flight
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

        binding.btnSelectFlight.setOnClickListener {
//            jika ticket habis show dialog
            showDialogTecketSoldOut()
        }

        val getBundleDetailFlight = arguments?.getSerializable("DATA_FLIGHT")

        if(getBundleDetailFlight != null){
            setDataFlight(getBundleDetailFlight as Flight)
            Log.i("DATA_DETAIL_FLIGHT", getBundleDetailFlight.toString())
        }


    }

    @SuppressLint("SetTextI18n")
    private fun setDataFlight(data: Flight) {
        //nanti diganti menggunakan data binding
        binding.tvFlightDestination.text = "${data.airportFrom} -> ${data.airportTo} (${data.duration/60} ${data.duration%60})"
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