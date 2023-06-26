package com.binar.finalproject.view.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.finalproject.R
import com.binar.finalproject.databinding.FragmentBiodataPenumpangBinding
import com.binar.finalproject.model.BiodataPassenger
import com.binar.finalproject.model.searchflight.FlightTicketOneTrip
import com.binar.finalproject.model.searchflight.FlightTicketRoundTrip
import com.binar.finalproject.model.transaction.request.Passenger
import com.binar.finalproject.utils.showCustomToast
import com.binar.finalproject.view.adapter.BiodataPassengerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BiodataPenumpangFragment : Fragment() {

    private lateinit var binding : FragmentBiodataPenumpangBinding
    private lateinit var biodataPassengerAdapter: BiodataPassengerAdapter
    private var flightTicketOneTrip = FlightTicketOneTrip()
    private var flightTicketRoundTrip = FlightTicketRoundTrip()
    private var arrSeatPassenger = IntArray(3)
    private val listPassenger = mutableListOf<BiodataPassenger>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBiodataPenumpangBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //get bundle
        val getListSeatPassenger = arguments?.getIntArray("DATA_LIST_NUM_SEAT")
        val getTypeRoundTrip = arguments?.getBoolean("TYPE_TRIP_ROUNDTRIP")
        val getDataPemesan = arguments?.getSerializable("DATA_PEMESAN")


        if (getTypeRoundTrip != null){
            if(getTypeRoundTrip == true){
                val getRoundTrip = arguments?.getSerializable("DATA_FLIGHT_ROUND_TRIP")
                flightTicketRoundTrip = getRoundTrip as FlightTicketRoundTrip
            }else{
                val getOneTrip = arguments?.getSerializable("DATA_FLIGHT_ONE_TRIP")
                flightTicketOneTrip = getOneTrip as FlightTicketOneTrip
            }
        }

        if (getListSeatPassenger != null) {
            arrSeatPassenger = getListSeatPassenger
        }
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.visibility = View.GONE

        if (getListSeatPassenger != null) {
            setRvBioPassenger(getListSeatPassenger)
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnLanjutPilihKursi.setOnClickListener {
//            if(getTypeRoundTrip != null){
//                if(getTypeRoundTrip){
//                    val putBundleDataFlight = Bundle().apply {
//                        putIntArray("DATA_LIST_NUM_SEAT", getListSeatPassenger)
//                        putBoolean("TYPE_TRIP_ROUNDTRIP", true)
//                        putSerializable("DATA_FLIGHT_ROUND_TRIP", flightTicketRoundTrip)
//                    }
//                    findNavController().navigate(R.id.action_biodataPenumpangFragment_to_checkoutFragment3, putBundleDataFlight)
//                }else{
//                    val putBundleDataFlight = Bundle().apply {
//                        putIntArray("DATA_LIST_NUM_SEAT", getListSeatPassenger)
//                        putBoolean("TYPE_TRIP_ROUNDTRIP", false)
//                        putSerializable("DATA_FLIGHT_ONE_TRIP", flightTicketOneTrip)
//                    }
//                    findNavController().navigate(R.id.action_biodataPenumpangFragment_to_checkoutFragment3, putBundleDataFlight)
//                }
//            }
            val bioIsNotEmpty = checkBioIsNotEmpty(biodataPassengerAdapter.getDataBioPassenger())
            Log.i("DATA_PASSENGGER", biodataPassengerAdapter.getDataBioPassenger().toString())
            if(bioIsNotEmpty){
                if(getTypeRoundTrip != null && getDataPemesan != null){
                    if(getTypeRoundTrip){
                        val putBundleDataFlight = Bundle().apply {
                            putIntArray("DATA_LIST_NUM_SEAT", getListSeatPassenger)
                            putBoolean("TYPE_TRIP_ROUNDTRIP", true)
                            putSerializable("DATA_FLIGHT_ROUND_TRIP", flightTicketRoundTrip)
                            putSerializable("DATA_PEMESAN", getDataPemesan)
                            putParcelableArrayList("DATA_PASSENGER", ArrayList(biodataPassengerAdapter.getDataBioPassenger()))
                        }
                        findNavController().navigate(R.id.action_biodataPenumpangFragment_to_selectSeatFragment, putBundleDataFlight)
                    }else{
                        val putBundleDataFlight = Bundle().apply {
                            putIntArray("DATA_LIST_NUM_SEAT", getListSeatPassenger)
                            putBoolean("TYPE_TRIP_ROUNDTRIP", false)
                            putSerializable("DATA_FLIGHT_ONE_TRIP", flightTicketOneTrip)
                            putSerializable("DATA_PEMESAN", getDataPemesan)
                            putParcelableArrayList("DATA_PASSENGER", ArrayList(biodataPassengerAdapter.getDataBioPassenger()))
                        }
                        findNavController().navigate(R.id.action_biodataPenumpangFragment_to_selectSeatFragment, putBundleDataFlight)
                    }
                }
                Log.i("DATA_PASSENGGER", biodataPassengerAdapter.getDataBioPassenger().toString())
            }
            else{
                Toast(requireContext()).showCustomToast(
                    "Data Passenger tidak boleh kosong!", requireActivity(), R.layout.toast_alert_red)
            }

        }
    }

    private fun setRvBioPassenger(item: IntArray) {
        listPassenger.clear()
        for(i in item.indices){
            for(z in 1 .. item[i]){
                when(i){
                    0 -> listPassenger.add(BiodataPassenger("Adult"))
                    1 -> listPassenger.add(BiodataPassenger("Child"))
                    2 -> listPassenger.add(BiodataPassenger("Baby"))
                }
            }

        }
        biodataPassengerAdapter = BiodataPassengerAdapter(listPassenger, requireContext(), requireActivity())

        binding.rvBioPassenger.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = biodataPassengerAdapter
        }
    }

    private fun checkBioIsNotEmpty(dataBioPassenger: List<Passenger>): Boolean {
        val bioNotEmpty = dataBioPassenger.all { passenger ->
                    passenger.type.toString().isNotEmpty() &&
                    passenger.birthday.toString().isNotEmpty() &&
                    passenger.expired.toString().isNotEmpty() &&
                    passenger.name.toString().isNotEmpty() &&
                    passenger.nationality.toString().isNotEmpty() &&
                    passenger.seat.toString().isNotEmpty() &&
                    passenger.issuedCountry.toString().isNotEmpty() &&
                    passenger.title.toString().isNotEmpty() &&
                    passenger.nik.toString().isNotEmpty()
        }
//        val bioNotEmpty = dataBioPassenger.any { passenger ->
//                    passenger.type.toString().isNotEmpty() &&
//                    passenger.birthday.toString().isNotEmpty() &&
//                    passenger.name.toString().isNotEmpty() &&
//                    passenger.nationality.toString().isNotEmpty() &&
//                    passenger.issuedCountry.toString().isNotEmpty() &&
//                    passenger.title.toString().isNotEmpty() &&
//                    passenger.nik.toString().isNotEmpty()
//        }
        return bioNotEmpty
    }

}