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
import com.binar.finalproject.utils.showCustomToast
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

        val getIdBundle = arguments?.getInt("ID_USER")
        binding.mintaKodeVertif.setOnClickListener {

            if (getIdBundle != null ){
                resendKodeOTP(getIdBundle)
            }
        }

        binding.btnVerif.setOnClickListener{
            verifikasiOTP()
        }


    }
    private fun resendKodeOTP(id : Int){
        userViewModel.resendOTP(id)
        userViewModel.resendResponseOtp.observe(viewLifecycleOwner){
            if (it != null){
                Toast.makeText(context, "Kode akan dikirim kembali via email", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()

            }
        }

    }

    private fun verifikasiOTP(){
        val kodeNo1 = binding.kode1.text.toString()
        val kodeNo2 = binding.kode2.text.toString()
        val kodeNo3 = binding.kode3.text.toString()
        val kodeNo4 = binding.kode4.text.toString()
        val kodeNo5 = binding.kode5.text.toString()
        val kodeNo6 = binding.kode6.text.toString()

        if (kodeNo1.isNotEmpty() && kodeNo2.isNotEmpty() && kodeNo3.isNotEmpty() && kodeNo4.isNotEmpty() && kodeNo5.isNotEmpty() && kodeNo6.isNotEmpty()){
            userViewModel.putVerificationOtp(PutDataOtp("$kodeNo1$kodeNo2$kodeNo3$kodeNo4$kodeNo5$kodeNo6"))
            userViewModel.responseOtp.observe(viewLifecycleOwner){
                if (it != null){
                    Toast(requireContext()).showCustomToast(
                        "Verifikasi berhasil !", requireActivity(), R.layout.toast_alert_green)
                    findNavController().navigate(R.id.action_otpFragment_to_loginFragment)
                }
            }
        }else{
            Toast(requireContext()).showCustomToast(
                "Kode OTP harus di isi!", requireActivity(), R.layout.toast_alert_red)
        }
    }


}