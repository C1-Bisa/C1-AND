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
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.binar.finalproject.R
import com.binar.finalproject.databinding.FragmentLoginBinding
import com.binar.finalproject.local.DataStoreUser
import com.binar.finalproject.model.resetpassword.PatchResetPassword
import com.binar.finalproject.model.user.login.PostLogin
import com.binar.finalproject.utils.showCustomToast
import com.binar.finalproject.viewmodel.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex


@AndroidEntryPoint
class LoginFragment : Fragment() {
    lateinit var binding : FragmentLoginBinding
    private lateinit var dataSotreUser : DataStoreUser
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

        dataSotreUser = DataStoreUser(requireContext().applicationContext)


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

        binding.tvLupaPassword.setOnClickListener {
            resetKataSandi()
        }


    }

    private fun getLogin() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()


        if (email.isNotEmpty() && password.isNotEmpty()){
            userLoginVm.postLogin(PostLogin(email, password))
            userLoginVm.responseLogin.observe(viewLifecycleOwner){
                Log.i("TAG LOGIN OBSERVER", "YA")

                if (it != null){
                    Log.i("TAG LOGIN OBSERVER", "1")
                    Log.i("RESPONSE_LOGIN", it.toString())
                    val emailUser1 = it.data.email
                    val tokenUser1 = it.data.token
                    Log.i("RESPONSE_LOGIN_USER", emailUser1 + tokenUser1)
                    if (emailUser1.isNotEmpty() && tokenUser1.isNotEmpty()) {

                        lifecycleScope.launch {
                            dataSotreUser.saveUser(emailUser1, tokenUser1)

                            if(dataSotreUser.isActiveToken().isNotEmpty()){
                                userLoginVm.responseLogin.removeObservers(viewLifecycleOwner)
                                Toast(requireContext()).showCustomToast(
                                    "Login Berhasil !",
                                    requireActivity(),
                                    R.layout.toast_alert_green
                                )


                                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                            }
                        }
                    }

                }else{
                    Log.i("TAG LOGIN OBSERVER", "2")
                    Toast(requireContext()).showCustomToast(
                        "Email dan password tidak terdaftar! ", requireActivity(), R.layout.toast_alert_red)

                }

            }

        }else if (email.isEmpty() && password.isEmpty()){
            Toast(requireContext()).showCustomToast(
                "Email dan password harus di isi!", requireActivity(), R.layout.toast_alert_red)

        }else if(email.isEmpty()){
            Toast(requireContext()).showCustomToast(
                "Email tidak boleh kosong !", requireActivity(), R.layout.toast_alert_red)

        }else if (password.isEmpty()){
            Toast(requireContext()).showCustomToast(
                "Password harus di isi !", requireActivity(), R.layout.toast_alert_red)
        }

    }

    private fun resetKataSandi(){
        val dataEmail = binding.etEmail.text.toString()


        if (dataEmail.isNotEmpty()){
            userLoginVm.patchResetPassword(PatchResetPassword(dataEmail))
            userLoginVm.responseResetPassword.observe(viewLifecycleOwner){
                if (it != null){
                    Toast(requireContext()).showCustomToast(
                        "Tautan reset password terkirim!", requireActivity(), R.layout.toast_alert_green)

                    findNavController().navigate(R.id.action_loginFragment_to_lupaPasswordFragment)

                }else{
                    Toast(requireContext()).showCustomToast(
                        "Terdapat error !", requireActivity(), R.layout.toast_alert_red)
                }
            }
        }
    }


}