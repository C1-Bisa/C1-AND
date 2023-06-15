package com.binar.finalproject.view.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.binar.finalproject.R
import com.binar.finalproject.databinding.FragmentLoginBinding
import com.binar.finalproject.local.DataStore
import com.binar.finalproject.model.user.login.PostLogin
import com.binar.finalproject.viewmodel.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment : Fragment() {
    lateinit var binding : FragmentLoginBinding
    private lateinit var dataSotreUser : DataStore
    private val userLoginVm: UserViewModel by viewModels()

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

        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.visibility = View.GONE

        dataSotreUser = DataStore(requireContext().applicationContext)


        binding.tvLupaPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_lupaPasswordFragment)
        }
        binding.tvDaftarDisini.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }


        binding.btnLogin.setOnClickListener {
            getLogin()

        }
        dataSotreUser.getEmailUser.asLiveData().observe(viewLifecycleOwner){email ->
            Log.i("EMAILUSER", email)

        }

        dataSotreUser.getToken.asLiveData().observe(viewLifecycleOwner){token ->
            Log.i("TOKEN", token)
        }


    }

    private fun getLogin() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()


        if (email.isNotEmpty() && password.isNotEmpty()){
            userLoginVm.postLogin(PostLogin(email, password))

            userLoginVm.responseLogin.observe(viewLifecycleOwner){
                if (it != null){
                    val emailUser1 = it.data.email
                    val tokenUser1 = it.data.token

                    if (emailUser1.isNotEmpty() && tokenUser1.isNotEmpty()){
                        lifecycleScope.launch {
                            dataSotreUser.saveUser(emailUser1, tokenUser1)
                        }


                    }
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }
            }

        }else{
            Toast.makeText(context, "Kata sandi harus di isi", Toast.LENGTH_SHORT).show()
        }

    }


}