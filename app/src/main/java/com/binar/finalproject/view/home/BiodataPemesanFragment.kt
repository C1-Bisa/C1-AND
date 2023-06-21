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

class BiodataPemesanFragment : Fragment() {

    private lateinit var binding: FragmentBiodataPemesanBinding
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

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSimpanBiodataPemesan.setOnClickListener {
//            checkField()
            val putBundleDataFlight = Bundle().apply {
                putIntArray("DATA_LIST_NUM_SEAT", getListSeatPassenger)
            }

            findNavController().navigate(R.id.action_biodataPemesanFragment_to_biodataPenumpangFragment, putBundleDataFlight)
        }

        binding.optionClan.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                binding.layoutNameClan.visibility = View.VISIBLE
            }else{
                binding.layoutNameClan.visibility = View.GONE
            }
        }

    }

    private fun checkField() {
        val fullName = binding.etNamaLengkapPemesan.text
        val nameClan = binding.etNameClan.text
        val phoneNum = binding.etNoTelephone.text
        val email = binding.etEmail.text

        if(binding.optionClan.isChecked){
            if(fullName.isNotEmpty() && nameClan.isNotEmpty() && phoneNum.isNotEmpty() && email.isNotEmpty()){
                findNavController().navigate(R.id.action_biodataPemesanFragment_to_biodataPenumpangFragment)
            }else{
                Toast.makeText(context, "Field tidak boleh kosong",Toast.LENGTH_SHORT).show()
            }
        }else{
            if(fullName.isNotEmpty() &&  phoneNum.isNotEmpty() && email.isNotEmpty()){
                findNavController().navigate(R.id.action_biodataPemesanFragment_to_biodataPenumpangFragment)
            }else{
                Toast.makeText(context, "Field tidak boleh kosong",Toast.LENGTH_SHORT).show()
            }
        }
    }

}