package com.binar.finalproject.view.home

import android.annotation.SuppressLint
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
import com.binar.finalproject.databinding.FragmentCheckoutBinding
import com.binar.finalproject.local.DataStoreUser
import com.binar.finalproject.model.getdetailflight.ListDetailFlight
import com.binar.finalproject.model.getdetailflight.PostDataFlight
import com.binar.finalproject.model.getdetailflight.datadetailflight.Berangkat
import com.binar.finalproject.model.getdetailflight.datadetailflight.Pulang
import com.binar.finalproject.model.searchflight.FlightTicketOneTrip
import com.binar.finalproject.model.searchflight.FlightTicketRoundTrip
import com.binar.finalproject.model.transaction.request.Flight
import com.binar.finalproject.model.transaction.request.Passenger
import com.binar.finalproject.model.transaction.request.RequestTransaction
import com.binar.finalproject.view.adapter.DetailFlightAdapter
import com.binar.finalproject.viewmodel.FlightViewModel
import com.binar.finalproject.viewmodel.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

@AndroidEntryPoint
class CheckoutFragment : Fragment() {

    private lateinit var binding : FragmentCheckoutBinding
    private val flightViewModel : FlightViewModel by viewModels()
    private val transactionViewModel : TransactionViewModel by viewModels()
    private lateinit var dataStoreUser : DataStoreUser

    private lateinit var detailFlightAdapter: DetailFlightAdapter


    private var flightTicketOneTrip = FlightTicketOneTrip()
    private var flightTicketRoundTrip = FlightTicketRoundTrip()
    private var listSeatPass = IntArray(3)

    private var amount : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCheckoutBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailFlightAdapter = DetailFlightAdapter(emptyList())

        dataStoreUser = DataStoreUser(requireContext().applicationContext)

        val getListSeatPassenger = arguments?.getIntArray("DATA_LIST_NUM_SEAT")
        val getTypeRoundTrip = arguments?.getBoolean("TYPE_TRIP_ROUNDTRIP")
        val getDataPemesan = arguments?.getSerializable("DATA_PEMESAN")
        val getDataPassenger = arguments?.getParcelableArrayList<Passenger>("DATA_PASSENGER")



        if (getTypeRoundTrip != null && getListSeatPassenger != null){
            if(getTypeRoundTrip == true){
                val getRoundTrip = arguments?.getSerializable("DATA_FLIGHT_ROUND_TRIP")
                flightTicketRoundTrip = getRoundTrip as FlightTicketRoundTrip

                setRvDataFlight(getTypeRoundTrip, getListSeatPassenger)
            }else{
                val getOneTrip = arguments?.getSerializable("DATA_FLIGHT_ONE_TRIP")
                flightTicketOneTrip = getOneTrip as FlightTicketOneTrip
                setRvDataFlight(getTypeRoundTrip, getListSeatPassenger)

            }
        }

        //cek nullable data pemesan dan data passenger
        if(getDataPemesan != null && getDataPassenger != null){
            Log.i("HASIL DATA PASS", "$getDataPemesan & $getDataPassenger")
            Log.i("HASIL DATA PASSANGER",
                (getDataPassenger as kotlin.collections.List<Passenger>).toString()
            )
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        if(getTypeRoundTrip != null && getListSeatPassenger != null){

            listSeatPass = getListSeatPassenger

            setDetailFlight(getTypeRoundTrip, listSeatPass)
        }

        binding.btnLanjutPembayaran.setOnClickListener {
            if(getTypeRoundTrip != null && amount != 0 && getDataPassenger != null){
                createTransaction(getTypeRoundTrip, getDataPassenger)
            }

        }

    }

    private fun createTransaction(roundTrip: Boolean, dataPassenger : List<Passenger>) {
        val listFlight = mutableListOf<Flight>()
        if (roundTrip){
            if(flightTicketRoundTrip.toString().isNotEmpty()){
                listFlight.add(Flight(flightTicketRoundTrip.flightIdDeparture, "Departure"))
                listFlight.add(Flight(flightTicketRoundTrip.flightIdReturn, "Arrival"))
            }
        }else{
            if(flightTicketOneTrip.toString().isNotEmpty()){
                listFlight.add(Flight(flightTicketOneTrip.flightIdDeparture,"Departure"))
            }
        }

        val totalPrice = amount

        val postTrasanction = RequestTransaction(
            totalPrice, listFlight, dataPassenger
        )

        dataStoreUser.getToken.asLiveData().observe(viewLifecycleOwner){
            if (it != null){
                if(postTrasanction.toString().isNotEmpty()){
                    transactionViewModel.createTransaction(postTrasanction, it)
                }
            }
        }

        transactionViewModel.responDataTransaction.observe(viewLifecycleOwner){
            if (it != null){
                Log.i("HASIL RESPON TRANSACTION", it.toString())
            }
        }



    }

    @SuppressLint("SetTextI18n")
    private fun setDetailFlight(getTypeRoundTrip: Boolean, listSeatPass: IntArray) {

        flightViewModel.detailFlight.observe(viewLifecycleOwner){
            if(it != null){

                if(getTypeRoundTrip){
                    detailFlightAdapter.setListFlight(listOf(
                        convertDataClassBerangkat(it.berangkat),
                        convertDataClassPulang(it.pulang)
                    ))
                }else{
                    detailFlightAdapter.setListFlight(listOf(
                        convertDataClassBerangkat(it.berangkat)
                    ))
                }


                Log.i("seat_pass", this.listSeatPass.toString())
                for(i in this.listSeatPass.indices){
                    if(this.listSeatPass[i] == 0){
                        when(i){
                            0 -> binding.layoutNumAdult.visibility = View.GONE
                            1 -> binding.layoutNumChild.visibility = View.GONE
                            2 -> binding.layoutNumBaby.visibility = View.GONE
                        }
                    }
                }

                binding.tvNumAdult.text = "${this.listSeatPass[0].toString()} Adult"
                binding.tvTotalPriceAdult.text = "IDR ${convertToCurrencyIDR(it.totalAdults)}"

                binding.tvNumChild.text = "${this.listSeatPass[1].toString()} Child"
                binding.tvTotalPriceChild.text = "IDR ${convertToCurrencyIDR(it.totalChild)}"

                binding.tvNumBaby.text = "${this.listSeatPass[2].toString()} Baby"
                binding.tvTotalPriceBaby.text = "IDR ${convertToCurrencyIDR(it.totalBaby)}"

                binding.tvTotalTax.text = "IDR ${convertToCurrencyIDR(it.tax)}"

                binding.tvTotalPromo.text = "IDR 0"

                binding.tvTotalPrice.text = "IRD ${convertToCurrencyIDR(it.totalPrice)}"

                amount = it.totalPrice
            }


        }
    }

    private fun setRvDataFlight(typeRoundTrip : Boolean, arrSeatPass : IntArray) {

        binding.rvDetailDataFlight.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = detailFlightAdapter
        }
        Log.i("TYPE_TRIP", "${typeRoundTrip.toString()} ${flightTicketRoundTrip.toString()} / ${flightTicketOneTrip.toString()}")


        flightViewModel.getDetailFlight(
            PostDataFlight(
                arrSeatPass[1],
                arrSeatPass[2],
                arrSeatPass[0],
                if(typeRoundTrip){
                    listOf(flightTicketRoundTrip.flightIdDeparture, flightTicketRoundTrip.flightIdReturn)
                }else{
                    listOf(flightTicketOneTrip.flightIdDeparture)
                }
            )
        )
//        Log.i("ID_DEPA", flightTicketOneTrip.flightIdDeparture.toString())
//        flightViewModel.getDetailFlight(
//            PostDataFlight(1,1,1, listOf(flightTicketOneTrip.flightIdDeparture)
//            )
//        )


    }

    private fun  convertDataClassBerangkat(data : Berangkat): ListDetailFlight {
        return ListDetailFlight(
            airline = data.airline,
            airportFrom = data.airportFrom,
            airportTo = data.airportTo,
            arrivalDate = data.arrivalDate,
            arrivalTime = data.arrivalTime,
            departureDate = data.departureDate,
            departureTime = data.departureTime,
            description = data.description,
            duration = data.duration,
            flightClass = data.flightClass,
            from = data.from,
            price = data.price,
            to = data.to
        )
    }

    private fun  convertDataClassPulang(data : Pulang): ListDetailFlight {
        return ListDetailFlight(
            airline = data.airline,
            airportFrom = data.airportFrom,
            airportTo = data.airportTo,
            arrivalDate = data.arrivalDate,
            arrivalTime = data.arrivalTime,
            departureDate = data.departureDate,
            departureTime = data.departureTime,
            description = data.description,
            duration = data.duration,
            flightClass = data.flightClass,
            from = data.from,
            price = data.price,
            to = data.to
        )
    }

    private fun convertToCurrencyIDR(price : Int) : String{
        val currencyFormatIDR = DecimalFormat("#,###", DecimalFormatSymbols.getInstance(Locale("id", "ID")))
        return currencyFormatIDR.format(price)

    }

}