package com.binar.finalproject.view.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.binar.finalproject.R
import com.binar.finalproject.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    lateinit var binding : FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            getLogin()
        }
    }

    private fun getLogin() {
        var email = binding.etEmail.text.toString()
        var password = binding.etPassword.text.toString()


        if (email.isNotEmpty() && password.isNotEmpty()){

            Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Kata sandi harus di isi", Toast.LENGTH_SHORT).show()
        }

    }


}