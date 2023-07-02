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
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.finalproject.R
import com.binar.finalproject.databinding.FragmentSelectSeatBinding
import com.binar.finalproject.model.searchflight.FlightTicketOneTrip
import com.binar.finalproject.model.searchflight.FlightTicketRoundTrip
import com.binar.finalproject.model.searchflight.SearchFlight
import com.binar.finalproject.model.seatconfiguration.ConfigurationSeat
import com.binar.finalproject.model.seatconfiguration.Seat
import com.binar.finalproject.model.transaction.request.Passenger
import com.binar.finalproject.utils.showCustomToast
import com.binar.finalproject.view.adapter.SeatAdapter
import com.binar.finalproject.view.adapter.SeatFlightAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable
import java.util.ArrayList

@AndroidEntryPoint
class SelectSeatFragment : Fragment() {

    private lateinit var binding: FragmentSelectSeatBinding
//    private lateinit var seatAdapter: SeatAdapter
    private lateinit var seatFlightAdapter: SeatFlightAdapter

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
        val getSearchFlight = arguments?.getSerializable("DATA_SEARCH")

        if (getTypeRoundTrip != null && getListSeatPassenger != null && getSearchFlight != null){
            val dataSearchFlight = getSearchFlight as SearchFlight
            if(getTypeRoundTrip == true){
                val getRoundTrip = arguments?.getSerializable("DATA_FLIGHT_ROUND_TRIP")
                flightTicketRoundTrip = getRoundTrip as FlightTicketRoundTrip

                val title = "Pilih Kursi (${dataSearchFlight.from} < > ${dataSearchFlight.to} - ${dataSearchFlight.flightClass}) "
                binding.tvTitleBar.text = title


            }else{
                val getOneTrip = arguments?.getSerializable("DATA_FLIGHT_ONE_TRIP")
                flightTicketOneTrip = getOneTrip as FlightTicketOneTrip

                val title = "Pilih Kursi (${dataSearchFlight.from} > ${dataSearchFlight.to} - ${dataSearchFlight.flightClass}) "
                binding.tvTitleBar.text = title
            }
        }

        if(getTypeRoundTrip != null && getListSeatPassenger != null && getSearchFlight != null){

            listSeatPass = getListSeatPassenger

            val dataSearch = getSearchFlight as SearchFlight

            var numPassenger = 0

            for(i in listSeatPass.indices){
                numPassenger += listSeatPass[i]
            }

            if(numPassenger != 0){
                seatFlightAdapter = if(getTypeRoundTrip){
                    SeatFlightAdapter(listOf(ConfigurationSeat(dataSearch.flightClass, "Kepergian ${dataSearch.from} > ${dataSearch.to}\n"), ConfigurationSeat(dataSearch.flightClass, "Kepulangan ${dataSearch.to} > ${dataSearch.from}\n")), numPassenger, true)
                }else{
                    SeatFlightAdapter(listOf(ConfigurationSeat(dataSearch.flightClass, "Keberangkatan ")), numPassenger, false)
                }
                setRecycleviewSeat()
            }

        }


        binding.btnSimpanSeat.setOnClickListener {
            if(getDataPassenger != null && getTypeRoundTrip != null && getDataPemesan != null && getListSeatPassenger != null){
                setPositionSeatPassenger(getDataPassenger, getTypeRoundTrip, flightTicketOneTrip, flightTicketRoundTrip, getDataPemesan, getListSeatPassenger)
            }
            Log.i("HASIL_SEAT", seatFlightAdapter.getSeat().toString())
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

        for (item in seatFlightAdapter.getSeat()){
            for(z in item){
                Log.i("ITEM", z)
                if(z.isEmpty()){
                    dataSeatComplete = false
                    Log.i("SEAT4", seatFlightAdapter.getSeat().toString())
                    break
                }
            }

            if(item.isEmpty()){
                dataSeatComplete = false
                Log.i("SEAT4", seatFlightAdapter.getSeat().toString())
                break
            }
        }

            if(dataSeatComplete){
                Log.i("SEAT5", seatFlightAdapter.getSeat().toString())
                for( i in dataPassenger.indices){
                    if(getTypeRoundTrip){
                        dataPassenger[i].seatDeparture = seatFlightAdapter.getSeat()[0][i]
                        dataPassenger[i].seatReturn = seatFlightAdapter.getSeat()[1][i]
                    }else{
                        dataPassenger[i].seatDeparture = seatFlightAdapter.getSeat()[0][i]
                    }
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
                Log.i("HASIL_DATA PASSENGER", dataPassenger.toString())

            }else{
                Toast(requireContext()).showCustomToast(
                    "Pilih tempat duduk sebelum checkout!", requireActivity(), R.layout.toast_alert_red)
            }

    }

    private fun setRecycleviewSeat() {

        binding.rvSeatFlight.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = seatFlightAdapter
        }
    }

}