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
import com.binar.finalproject.view.adapter.BiodataPassengerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class BiodataPenumpangFragment : Fragment() {

    private lateinit var binding : FragmentBiodataPenumpangBinding
    private lateinit var biodataPassengerAdapter: BiodataPassengerAdapter

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

        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.visibility = View.GONE

        if (getListSeatPassenger != null) {
            setRvBioPassenger(getListSeatPassenger)
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setRvBioPassenger(item: IntArray) {
        val listPassenger = mutableListOf<BiodataPassenger>()
        for(i in item.indices){
            for(z in 1 .. item[i]){
                when(i){
                    0 -> listPassenger.add(BiodataPassenger("Adult"))
                    1 -> listPassenger.add(BiodataPassenger("Child"))
                    2 -> listPassenger.add(BiodataPassenger("Baby"))
                }
            }

        }
        biodataPassengerAdapter = BiodataPassengerAdapter(listPassenger, requireContext())

        binding.rvBioPassenger.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = biodataPassengerAdapter
        }

        binding.btnLanjutPilihKursi.setOnClickListener {
            val bioIsNotEmpty = checkBioIsNotEmpty(biodataPassengerAdapter.getDataBioPassenger())

            if(bioIsNotEmpty){
                Toast.makeText(context,
                    biodataPassengerAdapter.getDataBioPassenger().toString(), Toast.LENGTH_SHORT).show()

                Log.i("DATA_PASSENGGER", biodataPassengerAdapter.getDataBioPassenger().toString())
            }
        }
    }

    private fun checkBioIsNotEmpty(dataBioPassenger: List<BiodataPassenger>): Boolean {
        val bioNotEmpty = dataBioPassenger.any { passenger ->
                    passenger.ageCategory.isNotEmpty() &&
                    passenger.titlePassenger.isNotEmpty() &&
                    passenger.namePassenger.isNotEmpty() &&
                    passenger.nameClan.isNotEmpty() &&
                    passenger.birthDate.isNotEmpty() &&
                    passenger.citizen.isNotEmpty() &&
                    passenger.idCardOrPassport.isNotEmpty() &&
                    passenger.issuingCountry.isNotEmpty()
        }
        return bioNotEmpty
    }

}