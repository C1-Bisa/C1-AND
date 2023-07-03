package com.binar.finalproject.view.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
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
import com.binar.finalproject.local.DataStoreUser
import com.binar.finalproject.model.payment.RequestTransactionCode
import com.binar.finalproject.model.printticket.RequestBodyPrintTicket
import com.binar.finalproject.model.transaction.response.DataTransaction
import com.binar.finalproject.model.transactionhistoryperid.request.RequestTransactionId
import com.binar.finalproject.model.transactionhistoryperid.response.Data
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
    private var token : String = ""
    private var idTransaction = 0
    private var transactionCode = ""
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
        val getIdTransaction = arguments?.getInt("ID_TRANSACTION")

        dataStoreUser = DataStoreUser(requireContext().applicationContext)

        if(getIdTransaction != null){
            idTransaction = getIdTransaction
            dataStoreUser.getToken.asLiveData().observe(viewLifecycleOwner){
                if(it != null){
                    setInformationFlightForPayment(it, getIdTransaction)
                    Log.d("SET DATA FLIGHT2", getIdTransaction.toString())
                }
            }
        }

        binding.layoutTitlePayment2.setOnClickListener {
            binding.layoutGopay.visibility = View.GONE

            if(binding.layoutCreditCard.isVisible){

                binding.layoutCreditCard.visibility = View.GONE
            }else{
                binding.layoutCreditCard.visibility = View.VISIBLE
            }
        }

        binding.layoutTitlePayment1.setOnClickListener {
            binding.layoutCreditCard.visibility = View.GONE
            if(binding.layoutGopay.isVisible){
                binding.layoutGopay.visibility = View.GONE
            }else{
                binding.layoutGopay.visibility = View.VISIBLE
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        dataStoreUser.getToken.asLiveData().observe(viewLifecycleOwner){
            if(it != null && it.isNotEmpty()){
                token = it
            }
        }

        binding.btnBayar.setOnClickListener {
            if(getIdTransaction != null){
                if(checkFieldPayment()){
                    if(token.isNotEmpty()){
                        payFlightTicket(token)
                    }
                }else{
                    Toast(requireContext()).showCustomToast(
                        "Metode pembayaran tidak boleh kosong!", requireActivity(), R.layout.toast_alert_red)
                }
            }

        }
    }

    private fun checkFieldPayment(): Boolean {
        return if(binding.layoutGopay.isVisible || binding.layoutCreditCard.isVisible){
            if(binding.layoutGopay.isVisible){
                binding.etUsernameGopay.text.isNotEmpty() && binding.etNomerGopayAkun.text.isNotEmpty() && binding.etExpiryDateGopay.text.isNotEmpty()
            }else{
                binding.etNumberCreditCard.text.isNotEmpty() && binding.etCVVCreditCard.text.isNotEmpty() && binding.etExpiryDateCreditCard.text.isNotEmpty() && binding.etHolderNameCreditCard.text.isNotEmpty()
            }
        }else{
            false
        }
    }

    private fun setInformationFlightForPayment(token : String, idTrans: Int) {
        transactionViewModel.getTransactionById(token, RequestTransactionId(idTrans))

        transactionViewModel.responseTransactionById.observe(viewLifecycleOwner){
            if(it != null){
                if(it.arrival?.flight != null){
                    binding.layoutReturnFlight.visibility = View.VISIBLE
                    setDataFlightToPayment(it, true)
                }else{
                    binding.layoutReturnFlight.visibility = View.GONE
                    setDataFlightToPayment(it, false)
                }
            }
        }
    }

    private fun setDataFlightToPayment(item: Data, roundTrip: Boolean) {
        binding.apply {
            tvPointDeparture.text = item.departure.flight.from
            tvPointArrive.text = item.departure.flight.to
            //perlu diubah durasinya
            tvTotalFlight.text = item.departure.flight.duration.toString()
            tvDateDeparture.text = setDate(item.departure.flight.departureDate)
            tvDateArrive.text = setDate(item.departure.flight.arrivalDate)

            //convert to format hh:ss
            tvTimeDeparture.text = timeFormate(item.departure.flight.departureTime)
            tvTimeArrive.text = timeFormate(item.departure.flight.arrivalTime)

            tvBookingCode.text = item.transaction.transactionCode
            tvSeatClass.text = item.departure.flight.flightClass

            //convert to idr
            val totalPrice = "IDR ${convertToCurrencyIDR(item.departure.transaction.amount)}"
            tvTotalPrice.text = totalPrice

            val listCountPassenger = listOf<Int>(item.passenger.adult,item.passenger.child, item.passenger.baby)

            var countPassenger : String = ""
            for (i in listCountPassenger.indices){
                if(listCountPassenger[i] != 0){
                    countPassenger += if(i == 0){
                        "${listCountPassenger[i]} Adults"
                    }else if(i == 1){
                        if(countPassenger.isNotEmpty()){
                            ", ${listCountPassenger[i]} Child"
                        }else{
                            "${listCountPassenger[i]} Child"
                        }
                    }else{
                        ", ${listCountPassenger[i]} Baby"
                    }
                }
            }
            binding.tvPassenger.text = countPassenger

            binding.tvTotalFlight.text = reformatDuration(item.departure.flight.duration.toString())

        }

        transactionCode = item.transaction.transactionCode

        if(roundTrip){

            Log.i("ROUND TRIP PAYMENT", "YES")
            binding.apply {
                tvPointReturn.text = item.arrival!!.flight.from
                tvPointArriveReturnTrip.text = item.arrival.flight.to
                tvTotalFlightReturn.text = item.arrival.flight.duration.toString()
                tvDateReturn.text = setDate(item.arrival.flight.departureDate)
                tvDateArriveReturnTrip.text = setDate(item.arrival.flight.arrivalDate)
                tvTimeReturn.text = timeFormate(item.arrival.flight.departureTime)
                tvTimeArriveReturnTrip.text = timeFormate(item.arrival.flight.arrivalTime)
                tvTotalFlightReturn.text = reformatDuration(item.arrival.flight.duration.toString())
            }
        }
    }


    private fun payFlightTicket(token: String) {
        if(transactionCode.isNotEmpty()){
            transactionViewModel.postPayment(
                RequestTransactionCode(transactionCode), token
            )
        }

        transactionViewModel.responsePayment.observe(viewLifecycleOwner){
            if(it != null && idTransaction != 0){
                Toast(requireContext()).showCustomToast(
                    "Pembayaran Berhasil", requireActivity(), R.layout.toast_alert_green)
                Log.i("ID TRANSACTION 1", transactionCode)
                showDialogShowTicket(idTransaction, token)
            }else{
                Toast(requireContext()).showCustomToast(
                    "Pembayaran gagal", requireActivity(), R.layout.toast_alert_red)
            }

        }
    }

    private fun showDialogShowTicket(id: Int, token : String) {
        //bottom sheet
        val dialog = BottomSheetDialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_payment_success)
        val bindingDialog = DialogPaymentSuccessBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialog.root)

        bindingDialog.btnClose.setOnClickListener {
            dialog.dismiss()
            findNavController().navigate(R.id.action_paymentFragment_to_homeFragment)
        }

        bindingDialog.btnTerbitkanTiket.setOnClickListener {
            if(id.toString().isNotEmpty()){
                transactionViewModel.printTicket(token, RequestBodyPrintTicket(id.toString()))

                transactionViewModel.responsePrintTicket.observe(viewLifecycleOwner){
                    if(it != null){
                        Toast(requireContext()).showCustomToast(
                            "Tiket sudah terkirim di email", requireActivity(), R.layout.toast_alert_green)

                        if(findNavController().currentDestination?.id == R.id.paymentFragment){
                            findNavController().navigate(R.id.action_paymentFragment_to_homeFragment)
                        }
                    }else{
                        Toast(requireContext()).showCustomToast(
                            "Tiket gagal terkirim", requireActivity(), R.layout.toast_alert_red)
                    }
                }
            }

            dialog.dismiss()

        }

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation;
        dialog.window?.setGravity(Gravity.BOTTOM);

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

    fun reformatDuration(duration: String): String {
        val text = duration.toCharArray()
            .filter { it != '9' }
            .toCharArray()
        return "${text[0]}h ${text[1]}m"
    }


}