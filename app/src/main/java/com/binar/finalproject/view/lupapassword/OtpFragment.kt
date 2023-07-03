package com.binar.finalproject.view.lupapassword

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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

    private  var idUser : Int = 0
    private var emailUser : String = ""
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
        val getEmailBundle = arguments?.getString("EMAIL_USER")

        setEditTextFocusable()

        if (getIdBundle != null && getEmailBundle != null){
            idUser = getIdBundle
            emailUser = getEmailBundle

            binding.tvNomor.text = emailUser
        }
        binding.mintaKodeVertif.setOnClickListener {

            if (getIdBundle != null ){
                resendKodeOTP(getIdBundle)
            }
        }

        binding.btnVerif.setOnClickListener{
            verifikasiOTP()
        }

        userViewModel.setToasMassenge()
        userViewModel.toastMessage.observe(viewLifecycleOwner){
            if(it != null){
                Toast(requireContext()).showCustomToast(
                    it, requireActivity(), R.layout.toast_alert_red)
            }
        }

        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }

    }





    private fun resendKodeOTP(id : Int){
        userViewModel.resendOTP(id)
        userViewModel.resendResponseOtp.observe(viewLifecycleOwner){
            if (it != null){
                Toast(requireContext()).showCustomToast(
                    "Kode telah dikirim", requireActivity(), R.layout.toast_alert_green)
            }else{
                Toast(requireContext()).showCustomToast(
                    "Terjadi error", requireActivity(), R.layout.toast_alert_red)

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
                    val bundleId = Bundle().apply {
                        putInt("ID_USER", idUser)
                        putString("EMAIL_USER", emailUser)
                    }
                    Toast(requireContext()).showCustomToast(
                        "Verifikasi berhasil !", requireActivity(), R.layout.toast_alert_green)
                    if(findNavController().currentDestination?.id == R.id.otpFragment){
                        try {
                            findNavController().navigate(R.id.action_otpFragment_to_loginFragment, bundleId)
                        } catch (e: IllegalArgumentException) {
                            Log.e("NavigationError", "Navigation action tidak ditemukan", e)
                        }
                    }


                }
            }
        }else{
            Toast(requireContext()).showCustomToast(
                "Kode OTP harus di isi!", requireActivity(), R.layout.toast_alert_red)
            if (idUser != 0){
                val idUserBundle = Bundle().apply {
                    putInt("ID_USER", idUser)
                    putString("EMAIL_USER", emailUser)
                }
                findNavController().navigate(R.id.action_otpFragment_self, idUserBundle)
            }

        }
    }

    private fun setEditTextFocusable() {
       binding.kode1.requestFocus()
       binding.kode1.addTextChangedListener(object : TextWatcher{
           override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
           override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
           override fun afterTextChanged(p0: Editable?) {
               if (p0?.length == 1) {
                   binding.kode2.requestFocus()
               }
           }

       })

        binding.kode2.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                if (p0?.length == 1) {
                    binding.kode3.requestFocus()
                }else if(p0?.isEmpty() == true){
                    binding.kode1.requestFocus()
                }
            }

        })
        binding.kode3.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                if (p0?.length == 1) {
                    binding.kode4.requestFocus()
                }else if(p0?.isEmpty() == true){
                    binding.kode2.requestFocus()
                }
            }

        })
        binding.kode4.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                if (p0?.length == 1) {
                    binding.kode5.requestFocus()
                }else if(p0?.isEmpty() == true){
                    binding.kode3.requestFocus()
                }
            }

        })
        binding.kode5.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                if (p0?.length == 1) {
                    binding.kode6.requestFocus()
                }else if(p0?.isEmpty() == true){
                    binding.kode4.requestFocus()
                }
            }

        })
        binding.kode6.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                if(p0?.isEmpty() == true){
                    binding.kode5.requestFocus()
                }
            }

        })
    }


}