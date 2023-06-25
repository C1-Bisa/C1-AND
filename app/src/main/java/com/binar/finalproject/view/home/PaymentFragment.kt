package com.binar.finalproject.view.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.binar.finalproject.R
import com.binar.finalproject.databinding.DialogPaymentSuccessBinding
import com.binar.finalproject.databinding.FragmentPaymentBinding
import com.binar.finalproject.databinding.PassangerDialogLayoutBinding
import com.binar.finalproject.local.DataStoreUser
import com.binar.finalproject.model.payment.RequestTransactionCode
import com.binar.finalproject.model.transaction.request.RequestTransaction
import com.binar.finalproject.model.transaction.response.DataTransaction
import com.binar.finalproject.utils.showCustomToast
import com.binar.finalproject.viewmodel.TransactionViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@AndroidEntryPoint
class PaymentFragment : Fragment() {

    private lateinit var binding : FragmentPaymentBinding
    private val transactionViewModel: TransactionViewModel by viewModels()
    private lateinit var dataStoreUser: DataStoreUser
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPaymentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getDataTransaction = arguments?.getSerializable("DATA_TRANSACTION")

        dataStoreUser = DataStoreUser(requireContext().applicationContext)

        if(getDataTransaction != null){
            setInformationFlight(getDataTransaction as DataTransaction)
        }

        binding.btnExpandCreditCard.setOnClickListener {
            if(binding.layoutCreditCard.isVisible){

                binding.layoutCreditCard.visibility = View.GONE
            }else{
                binding.layoutCreditCard.visibility = View.VISIBLE
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnBayar.setOnClickListener {
            if(getDataTransaction != null){
                dataStoreUser.getToken.asLiveData().observe(viewLifecycleOwner){
                    if(it != null && it.isNotEmpty()){
                        payFlightTicket(getDataTransaction as DataTransaction, it)
                    }
                }

            }

        }
    }

    private fun payFlightTicket(data: DataTransaction, token: String) {
        if(data.transaction.transactionCode.isNotEmpty()){
            transactionViewModel.postPayment(
                RequestTransactionCode(data.transaction.transactionCode), token
            )
        }

        transactionViewModel.responsePayment.observe(viewLifecycleOwner){
            if(it != null){
                Toast(requireContext()).showCustomToast(
                    "Pembayaran Berhasil", requireActivity(), R.layout.toast_alert_green)
                showDialogShowTicket()
            }else{
                Toast(requireContext()).showCustomToast(
                    "Pembayaran gagal", requireActivity(), R.layout.toast_alert_red)
            }
        }
    }

    private fun showDialogShowTicket() {
        //bottom sheet
        val dialog = BottomSheetDialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_payment_success)
        val bindingDialog = DialogPaymentSuccessBinding.inflate(layoutInflater)
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

    private fun setInformationFlight(data: DataTransaction) {
        if(data.arrival.isNotEmpty()){
            binding.layoutReturnFlight.visibility = View.VISIBLE
            setDataFlight(data, true)
        }else{
            binding.layoutReturnFlight.visibility = View.GONE
            setDataFlight(data, false)
        }
    }

    private fun setDataFlight(data: DataTransaction, isRoundTrip : Boolean) {


        //PERMASALAHAN DATA DEPARTURE DAN RETURN KETIKA ROUND TRIP MENJADI DOUBLE

        val dataFlight = data.departure[0]
        binding.apply {
            tvPointDeparture.text = dataFlight.flight.from
            tvPointArrive.text = dataFlight.flight.to
            //perlu diubah durasinya
            tvTotalFlight.text = dataFlight.flight.duration.toString()
            tvDateDeparture.text = setDate(dataFlight.flight.departureDate)
            tvDateArrive.text = setDate(dataFlight.flight.arrivalDate)

            //convert to format hh:ss
            tvTimeDeparture.text = timeFormate(dataFlight.flight.departureTime)
            tvTimeArrive.text = timeFormate(dataFlight.flight.arrivalTime)

            tvBookingCode.text = data.transaction.transactionCode.toString()
            tvSeatClass.text = dataFlight.flight.flightClass

            //convert to idr
            val totalPrice = "IDR ${convertToCurrencyIDR(dataFlight.transaction.amount)}"
            tvTotalPrice.text = totalPrice

        }

        if(isRoundTrip){
            val dataFlight = data.arrival[0]
            binding.apply {
                tvPointReturn.text = dataFlight.flightArrival.from
                tvPointArriveReturnTrip.text = dataFlight.flightArrival.to
                //perlu diubah durasinya
                tvTotalFlightReturn.text = dataFlight.flightArrival.duration.toString()
                tvDateReturn.text = setDate(dataFlight.flightArrival.departureDate)
                tvDateArriveReturnTrip.text = setDate(dataFlight.flightArrival.arrivalDate)
                tvTimeReturn.text = timeFormate(dataFlight.flightArrival.departureTime)
                tvTimeArriveReturnTrip.text = timeFormate(dataFlight.flightArrival.arrivalTime)
            }
        }
    }

    private fun convertToCurrencyIDR(price : Int) : String{
        val currencyFormatIDR = DecimalFormat("#,###", DecimalFormatSymbols.getInstance(Locale("id", "ID")))
        return currencyFormatIDR.format(price)

    }

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

}