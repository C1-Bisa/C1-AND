package com.binar.finalproject.view.akun

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.binar.finalproject.R
import com.binar.finalproject.databinding.FragmentUpdateProfileBinding
import com.binar.finalproject.model.user.updateprofile.PutDataUpdateProfile
import com.binar.finalproject.viewmodel.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateProfileFragment : Fragment() {

    private lateinit var binding : FragmentUpdateProfileBinding
    private val userViewModel : UserViewModel by viewModels()

    //contoh
    private lateinit var token : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.visibility = View.GONE

        token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTQsImVtYWlsIjoiemVlemVyby4yMWluY0BnbWFpbC5jb20iLCJuYW1hIjoiWmVlIEFOWHkiLCJyb2xlIjoiVXNlciIsImlhdCI6MTY4Njk5OTI0NSwiZXhwIjoxNjg3MDAyODQ1fQ.wxTlG7HMX6jLxAJAC8S1NDz-PLmkXK7dk2XyVSTQy2M"

        //nanti dikasih kondisi apakah toke null atau tidak
        setFillEditTextProfile()
        binding.btnUpdateProfile.setOnClickListener {
            updateProfileUser()
        }
    }
    //set nilai pada fillEditText
    private fun setFillEditTextProfile() {
        userViewModel.getUserProfile(token)
        userViewModel.responseDataProfile.observe(viewLifecycleOwner){
            if(it != null){
                binding.etFullName.setText(it.data.nama)
                binding.etNoTelephone.setText(it.data.phone)
                binding.etEmail.setText(it.data.email)
            }
        }
    }

    private fun updateProfileUser() {
        val fullName = binding.etFullName.text.toString()
        val phoneNumber = binding.etNoTelephone.text.toString()

        if(fullName.isNotEmpty() && phoneNumber.isNotEmpty()){
            userViewModel.updateProfileUser(token,PutDataUpdateProfile(fullName, phoneNumber))

            userViewModel.responseUpdateProfile.observe(viewLifecycleOwner){
                if(it != null){
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    binding.etFullName.setText(it.data.nama)
                    binding.etNoTelephone.setText(it.data.phone)
                }else{
                    Toast.makeText(context, "Update profile gagal!", Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            Toast.makeText(context, "Data tidak boleh kosong!", Toast.LENGTH_SHORT).show()
        }



    }

}