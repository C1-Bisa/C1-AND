package com.binar.finalproject.view.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.binar.finalproject.R
import com.binar.finalproject.databinding.FragmentBiodataPemesanBinding
import com.binar.finalproject.local.DataStoreUser
import com.binar.finalproject.model.bio.BiodataPemesan
import com.binar.finalproject.model.searchflight.FlightTicketOneTrip
import com.binar.finalproject.model.searchflight.FlightTicketRoundTrip
import com.binar.finalproject.utils.showCustomToast
import com.binar.finalproject.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BiodataPemesanFragment : Fragment() {

    private lateinit var binding: FragmentBiodataPemesanBinding
    private var flightTicketOneTrip = FlightTicketOneTrip()
    private var flightTicketRoundTrip = FlightTicketRoundTrip()

    private val userViewModel : UserViewModel by viewModels()
    private lateinit var dataStoreUser: DataStoreUser
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBiodataPemesanBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //get bundle
        val getListSeatPassenger = arguments?.getIntArray("DATA_LIST_NUM_SEAT")
        val getTypeRoundTrip = arguments?.getBoolean("TYPE_TRIP_ROUNDTRIP")
        val getSearchFlight = arguments?.getSerializable("DATA_SEARCH")

        //initial data store
        dataStoreUser = DataStoreUser(requireContext().applicationContext)

        dataStoreUser.getToken.asLiveData().observe(viewLifecycleOwner){
            if(it != null){
                userViewModel.getUserProfile(it)
            }else{
                Log.e("TOKEN_NULL", "TOKEN_NULL")
            }
        }

        userViewModel.responseDataProfile.observe(viewLifecycleOwner){
            if(it != null){
                binding.etNamaLengkapPemesan.setText(it.data.nama)
                binding.etNameClan.setText("Sanjaya")
                binding.etEmail.setText(it.data.email)
                binding.etNoTelephone.setText(it.data.phone)

                binding.apply {
                    etNamaLengkapPemesan.isEnabled = false
                    etNameClan.isEnabled = false
                    etEmail.isEnabled = false
                    etNoTelephone.isEnabled = false
                }
            }
        }

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
//            if(true){
                if(getTypeRoundTrip != null && getSearchFlight != null){
                    if(getTypeRoundTrip){
                        val putBundleDataFlight = Bundle().apply {
                            putIntArray("DATA_LIST_NUM_SEAT", getListSeatPassenger)
                            putBoolean("TYPE_TRIP_ROUNDTRIP", true)
                            putSerializable("DATA_FLIGHT_ROUND_TRIP", flightTicketRoundTrip)
                            putSerializable("DATA_PEMESAN", setDataPemesan())
                            putSerializable("DATA_SEARCH", getSearchFlight)
                        }

                        findNavController().navigate(R.id.action_biodataPemesanFragment_to_biodataPenumpangFragment, putBundleDataFlight)
                    }else{
                        val putBundleDataFlight = Bundle().apply {
                            putIntArray("DATA_LIST_NUM_SEAT", getListSeatPassenger)
                            putBoolean("TYPE_TRIP_ROUNDTRIP", false)
                            putSerializable("DATA_FLIGHT_ONE_TRIP", flightTicketOneTrip)
                            putSerializable("DATA_PEMESAN", setDataPemesan())
                            putSerializable("DATA_SEARCH", getSearchFlight)
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
                Toast(requireContext()).showCustomToast(
                    "Field tidak boleh kosong", requireActivity(), R.layout.toast_alert_red)
                false
            }
        }else{
            if(fullName.isNotEmpty() &&  phoneNum.isNotEmpty() && email.isNotEmpty()){
                true
            }else{
                Toast(requireContext()).showCustomToast(
                    "Field tidak boleh kosong", requireActivity(), R.layout.toast_alert_red)
                false
            }
        }
    }

    private fun setDataPemesan() : BiodataPemesan{
        val nama = binding.etNamaLengkapPemesan.text.toString()
//        val familyName = binding.etNameClan.toString()
        val familyName = "Sanjaya"
        val phoneNumber = binding.etNoTelephone.text.toString()
        val email = binding.etEmail.text.toString()

        return BiodataPemesan(nama,familyName,phoneNumber,email)

    }


}