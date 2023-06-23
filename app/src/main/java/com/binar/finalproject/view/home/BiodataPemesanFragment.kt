package com.binar.finalproject.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.binar.finalproject.R
import com.binar.finalproject.databinding.FragmentBiodataPemesanBinding
import com.binar.finalproject.model.searchflight.FlightTicketOneTrip
import com.binar.finalproject.model.searchflight.FlightTicketRoundTrip

class BiodataPemesanFragment : Fragment() {

    private lateinit var binding: FragmentBiodataPemesanBinding
    private var flightTicketOneTrip = FlightTicketOneTrip()
    private var flightTicketRoundTrip = FlightTicketRoundTrip()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBiodataPemesanBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //get bundle
        val getListSeatPassenger = arguments?.getIntArray("DATA_LIST_NUM_SEAT")
        val getTypeRoundTrip = arguments?.getBoolean("TYPE_TRIP_ROUNDTRIP")


        if (getTypeRoundTrip != null){
            if(getTypeRoundTrip){
                val getRoundTrip = arguments?.getSerializable("DATA_FLIGHT_ROUND_TRIP")
                flightTicketRoundTrip = getRoundTrip as FlightTicketRoundTrip
            }else{
                val getOneTrip = arguments?.getSerializable("DATA_FLIGHT_ONE_TRIP")
                flightTicketOneTrip = getOneTrip as FlightTicketOneTrip
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSimpanBiodataPemesan.setOnClickListener {
            if(checkField()){
                if(getTypeRoundTrip != null){
                    if(getTypeRoundTrip){
                        val putBundleDataFlight = Bundle().apply {
                            putIntArray("DATA_LIST_NUM_SEAT", getListSeatPassenger)
                            putBoolean("TYPE_TRIP_ROUNDTRIP", true)
                            putSerializable("DATA_FLIGHT_ROUND_TRIP", flightTicketRoundTrip)
                        }

                        findNavController().navigate(R.id.action_biodataPemesanFragment_to_biodataPenumpangFragment, putBundleDataFlight)
                    }else{
                        val putBundleDataFlight = Bundle().apply {
                            putIntArray("DATA_LIST_NUM_SEAT", getListSeatPassenger)
                            putBoolean("TYPE_TRIP_ROUNDTRIP", false)
                            putSerializable("DATA_FLIGHT_ONE_TRIP", flightTicketOneTrip)
                        }

                        findNavController().navigate(R.id.action_biodataPemesanFragment_to_biodataPenumpangFragment, putBundleDataFlight)
                    }
                }
            }


        }

        binding.optionClan.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                binding.layoutNameClan.visibility = View.VISIBLE
            }else{
                binding.layoutNameClan.visibility = View.GONE
            }
        }

    }

    private fun checkField() : Boolean {
        val fullName = binding.etNamaLengkapPemesan.text
        val nameClan = binding.etNameClan.text
        val phoneNum = binding.etNoTelephone.text
        val email = binding.etEmail.text

        return if(binding.optionClan.isChecked){
            if(fullName.isNotEmpty() && nameClan.isNotEmpty() && phoneNum.isNotEmpty() && email.isNotEmpty()){
                true
            }else{
                Toast.makeText(context, "Field tidak boleh kosong",Toast.LENGTH_SHORT).show()
                false
            }
        }else{
            if(fullName.isNotEmpty() &&  phoneNum.isNotEmpty() && email.isNotEmpty()){
                true
            }else{
                Toast.makeText(context, "Field tidak boleh kosong",Toast.LENGTH_SHORT).show()
                false
            }
        }
    }

}