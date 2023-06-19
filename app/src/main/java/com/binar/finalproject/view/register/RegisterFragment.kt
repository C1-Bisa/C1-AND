package com.binar.finalproject.view.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.finalproject.R
import com.binar.finalproject.databinding.FragmentRegisterBinding
import com.binar.finalproject.model.user.PostRegister
import com.binar.finalproject.utils.showCustomToast
import com.binar.finalproject.viewmodel.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterFragment : Fragment() {
    // viewmodel
    private val userViewModel : UserViewModel by viewModels()

    lateinit var binding: FragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.visibility = View.GONE

        binding.btnDaftar.setOnClickListener {
            getRegister()

        }
    }

    private fun getRegister() {
        val namaLengkap = binding.etMasukanNamaRegister.text.toString()
        val email = binding.etMasukanEmailRegister.text.toString()
        val nomorTelepon = binding.etMasukanNomorTelepon.text.toString()
        val password = binding.etMasukanPasswordRegister.text.toString()




        if (namaLengkap.isNotEmpty() && email.isNotEmpty() && nomorTelepon.isNotEmpty() && password.isNotEmpty()){
            userViewModel.postRegist(PostRegister(email, namaLengkap, password, nomorTelepon))
            userViewModel.responseUserRegist.observe(viewLifecycleOwner){

                // response message belom berhasil
                if (it != null){
                    val idBundle = Bundle().apply {
                        putInt("ID_USER", it.data.user.id)
                    }
                    Toast(requireContext()).showCustomToast(
                        "Kode OTP telah dikirim", requireActivity(), R.layout.toast_alert_green)
                    findNavController().navigate(R.id.action_registerFragment_to_otpFragment, idBundle)
                }else if (password.length < 8){
                    Toast(requireContext()).showCustomToast(
                        "Password minimal 8 karakter !", requireActivity(), R.layout.toast_alert_red)
                }
            }
        }else{
            Toast(requireContext()).showCustomToast(
                "Kata sandi harus di isi", requireActivity(), R.layout.toast_alert_red)
        }
    }


}