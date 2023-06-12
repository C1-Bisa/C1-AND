package com.binar.finalproject.view.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.binar.finalproject.R
import com.binar.finalproject.databinding.FragmentLoginBinding
import com.binar.finalproject.databinding.FragmentRegisterBinding
import com.binar.finalproject.model.user.PostRegister
import com.binar.finalproject.viewmodel.UserViewModel
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
        binding.btnDaftar.setOnClickListener {
            getRegister()
        }
    }

    private fun getRegister() {
        var namaLengkap = binding.etMasukanNamaRegister.text.toString()
        var email = binding.etMasukanEmailRegister.text.toString()
        var nomorTelepon = binding.etMasukanNomorTelepon.text.toString()
        var password = binding.etMasukanPasswordRegister.text.toString()


        if (namaLengkap.isNotEmpty() && email.isNotEmpty() && nomorTelepon.isNotEmpty() && password.isNotEmpty()){
            userViewModel.postRegist(PostRegister(email, namaLengkap, password, nomorTelepon))
            userViewModel.responseUserRegist.observe(viewLifecycleOwner){
                if (it != null){
                    Toast.makeText(context, "Register Successful", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context, "Kata sandi harus di isi", Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            Toast.makeText(context, "Kata sandi harus di isi", Toast.LENGTH_SHORT).show()
        }
    }


}