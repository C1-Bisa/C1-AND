package com.binar.finalproject.view.lupapassword

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.finalproject.R
import com.binar.finalproject.databinding.FragmentOtpBinding
import com.binar.finalproject.model.otpcode.PutDataOtp
import com.binar.finalproject.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtpFragment : Fragment() {

    private val userViewModel : UserViewModel by viewModels()


    private lateinit var binding : FragmentOtpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentOtpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        binding.btnVerif.setOnClickListener{
            val kodeNo1 = binding.etKode1.text.toString()
            val kodeNo2 = binding.etKode2.text.toString()
            val kodeNo3 = binding.etKode3.text.toString()
            val kodeNo4 = binding.etKode4.text.toString()
            val kodeNo5 = binding.etKode5.text.toString()
            val kodeNo6 = binding.etKode6.text.toString()

            if (kodeNo1.isNotEmpty() && kodeNo2.isNotEmpty() && kodeNo3.isNotEmpty() && kodeNo4.isNotEmpty() && kodeNo5.isNotEmpty() && kodeNo6.isNotEmpty()){
                userViewModel.putVerificationOtp(PutDataOtp("$kodeNo1$kodeNo2$kodeNo3$kodeNo4$kodeNo5$kodeNo6"))
                userViewModel.responseOtp.observe(viewLifecycleOwner){
                    if (it != null){
                        Toast.makeText(context, "Verifikasi berhasil", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_otpFragment_to_loginFragment)
                    }
                }
            }else{
                    Toast.makeText(context, "Nomor verifikasi harus terisi", Toast.LENGTH_SHORT).show()
            }
        }


    }


}