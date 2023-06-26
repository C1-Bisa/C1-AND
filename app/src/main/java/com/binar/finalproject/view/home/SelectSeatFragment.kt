package com.binar.finalproject.view.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.binar.finalproject.R
import com.binar.finalproject.databinding.FragmentSelectSeatBinding
import com.binar.finalproject.model.searchflight.FlightTicketOneTrip
import com.binar.finalproject.model.searchflight.FlightTicketRoundTrip
import com.binar.finalproject.model.seatconfiguration.Seat
import com.binar.finalproject.model.transaction.request.Passenger
import com.binar.finalproject.utils.showCustomToast
import com.binar.finalproject.view.adapter.SeatAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable
import java.util.ArrayList

@AndroidEntryPoint
class SelectSeatFragment : Fragment() {

    private lateinit var binding: FragmentSelectSeatBinding
    private lateinit var seatAdapter: SeatAdapter

    private var flightTicketOneTrip = FlightTicketOneTrip()
    private var flightTicketRoundTrip = FlightTicketRoundTrip()
    private var listSeatPass = IntArray(3)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSelectSeatBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.visibility = View.GONE

        val getListSeatPassenger = arguments?.getIntArray("DATA_LIST_NUM_SEAT")
        val getTypeRoundTrip = arguments?.getBoolean("TYPE_TRIP_ROUNDTRIP")
        val getDataPemesan = arguments?.getSerializable("DATA_PEMESAN")
        val getDataPassenger = arguments?.getParcelableArrayList<Passenger>("DATA_PASSENGER")

        if (getTypeRoundTrip != null && getListSeatPassenger != null){
            if(getTypeRoundTrip == true){
                val getRoundTrip = arguments?.getSerializable("DATA_FLIGHT_ROUND_TRIP")
                flightTicketRoundTrip = getRoundTrip as FlightTicketRoundTrip

            }else{
                val getOneTrip = arguments?.getSerializable("DATA_FLIGHT_ONE_TRIP")
                flightTicketOneTrip = getOneTrip as FlightTicketOneTrip
            }
        }

        if(getTypeRoundTrip != null && getListSeatPassenger != null){

            listSeatPass = getListSeatPassenger

            var numPassenger = 0

            for(i in listSeatPass.indices){
                numPassenger += listSeatPass[i]
            }

            if(numPassenger != 0){
                seatAdapter = SeatAdapter(emptyList(), numPassenger)
                setRecycleviewSeat()
            }

        }


        seatAdapter.itemSeatOnClick = {
            Log.i("HASIL_SEAT", it.seatCode)
        }

        binding.btnSimpanSeat.setOnClickListener {
            if(getDataPassenger != null && getTypeRoundTrip != null && getDataPemesan != null && getListSeatPassenger != null){
                setPositionSeatPassenger(getDataPassenger, getTypeRoundTrip, flightTicketOneTrip, flightTicketRoundTrip, getDataPemesan, getListSeatPassenger)
            }
            Log.i("HASIL_SEAT", seatAdapter.getConfigurationSeatPass().toString())
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setPositionSeatPassenger(
        data: ArrayList<Passenger>,
        getTypeRoundTrip: Boolean,
        flightTicketOneTrip: FlightTicketOneTrip,
        flightTicketRoundTrip: FlightTicketRoundTrip,
        getDataPemesan: Serializable,
        getListSeatPassenger: IntArray
    ) {
        var dataSeatComplete = true
        var dataPassenger = data

        for (i in seatAdapter.getConfigurationSeatPass().indices){
            if(seatAdapter.getConfigurationSeatPass()[i].isEmpty()){
                dataSeatComplete = false
                break
            }
        }

        if(dataSeatComplete){
            for( i in dataPassenger.indices){
                dataPassenger[i].seat = seatAdapter.getConfigurationSeatPass()[i]
            }

            if(getTypeRoundTrip){
                if(flightTicketRoundTrip.toString().isNotEmpty()){
                    val putBundleDataFlight = Bundle().apply {
                        putIntArray("DATA_LIST_NUM_SEAT", getListSeatPassenger)
                        putBoolean("TYPE_TRIP_ROUNDTRIP", true)
                        putSerializable("DATA_FLIGHT_ROUND_TRIP", flightTicketRoundTrip)
                        putSerializable("DATA_PEMESAN", getDataPemesan)
                        putParcelableArrayList("DATA_PASSENGER", ArrayList(dataPassenger))
                    }
                    findNavController().navigate(R.id.action_selectSeatFragment_to_checkoutFragment, putBundleDataFlight)
                }

            }else{
                if(flightTicketOneTrip.toString().isNotEmpty()){
                    val putBundleDataFlight = Bundle().apply {
                        putIntArray("DATA_LIST_NUM_SEAT", getListSeatPassenger)
                        putBoolean("TYPE_TRIP_ROUNDTRIP", false)
                        putSerializable("DATA_FLIGHT_ONE_TRIP", flightTicketOneTrip)
                        putSerializable("DATA_PEMESAN", getDataPemesan)
                        putParcelableArrayList("DATA_PASSENGER", ArrayList(dataPassenger))
                    }
                    findNavController().navigate(R.id.action_selectSeatFragment_to_checkoutFragment, putBundleDataFlight)
                }
            }

        }else{
            Toast(requireContext()).showCustomToast(
                "Pilih tempat duduk sebelum checkout!", requireActivity(), R.layout.toast_alert_red)
        }

    }

    private fun setRecycleviewSeat() {
        binding.rvSeat.apply {
            layoutManager = GridLayoutManager(context, 7)
            adapter = seatAdapter
        }

        seatAdapter.setListSeat(setListSeat())
    }


    private fun setListSeat() : List<Seat>{
        return listOf(
            Seat("A1", true) ,
            Seat("B1", false),
            Seat("C1", true),
            Seat("1", true),
            Seat("D1", true),
            Seat("E1", false),
            Seat("F1", true),
            Seat("A2", true) ,
            Seat("B2", false),
            Seat("C2", false),
            Seat("2", true),
            Seat("D2", true),
            Seat("E2", false),
            Seat("F2", true),
            Seat("A3", false) ,
            Seat("B3", false),
            Seat("C3", true),
            Seat("3", true),
            Seat("D3", false),
            Seat("E3", true),
            Seat("F3", false),
            Seat("A4", true) ,
            Seat("B4", false),
            Seat("C4", false),
            Seat("4", true),
            Seat("D4", true),
            Seat("E4", false),
            Seat("F4", false),
            Seat("A5", true) ,
            Seat("B5", false),
            Seat("C5", true),
            Seat("5", true),
            Seat("D5", true),
            Seat("E5", false),
            Seat("F5", false),
            Seat("A6", true) ,
            Seat("B6", false),
            Seat("C6", true),
            Seat("6", true),
            Seat("D6", true),
            Seat("E6", false),
            Seat("F6", true),
            Seat("A7", false) ,
            Seat("B7", false),
            Seat("C7", false),
            Seat("7", true),
            Seat("D7", false),
            Seat("E7", false),
            Seat("F7", true),
            Seat("A8", true) ,
            Seat("B8", false),
            Seat("C8", true),
            Seat("8", true),
            Seat("D8", true),
            Seat("E8", false),
            Seat("F8", true),
            Seat("A9", true) ,
            Seat("B9", false),
            Seat("C9", true),
            Seat("9", true),
            Seat("D9", false),
            Seat("E9", false),
            Seat("F9", true),
            Seat("A10", true) ,
            Seat("B10", true),
            Seat("C10", true),
            Seat("10", true),
            Seat("D10", false),
            Seat("E10", true),
            Seat("F10", true),
            Seat("A11", true) ,
            Seat("B11", false),
            Seat("C11", true),
            Seat("11", true),
            Seat("D11", true),
            Seat("E11", false),
            Seat("F11", true),
            Seat("A12", true) ,
            Seat("B12", true),
            Seat("C12", true),
            Seat("12", true),
            Seat("D12", false),
            Seat("E12", true),
            Seat("F12", true)
        )
    }

}