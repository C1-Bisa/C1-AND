package com.binar.finalproject.view.akun

import android.annotation.SuppressLint
import android.os.Bundle
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
import com.binar.finalproject.databinding.FragmentAkunBinding
import com.binar.finalproject.local.DataStoreUser
import com.binar.finalproject.utils.showCustomToast
import com.binar.finalproject.viewmodel.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AkunFragment : Fragment() {

    private lateinit var binding: FragmentAkunBinding
    private val userVm : UserViewModel by viewModels()
    private lateinit var dataSotreUser : DataStoreUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAkunBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.visibility = View.VISIBLE

        binding.updateProfile.setOnClickListener {
            findNavController().navigate(R.id.action_akunFragment_to_updateProfileFragment)
        }
        binding.btnMasukAkun.setOnClickListener {
            findNavController().navigate(R.id.action_akunFragment_to_loginFragment)
        }

        dataSotreUser = DataStoreUser(requireContext().applicationContext)

            if (dataSotreUser.isAlreadyLogin()) {
                binding.layoutUserLogged.visibility = View.VISIBLE
                binding.layoutUserNotLogged.visibility = View.GONE
            } else {
                binding.layoutUserLogged.visibility = View.GONE
                binding.layoutUserNotLogged.visibility = View.VISIBLE
            }

        binding.Btnlogout.setOnClickListener {


            userVm.postLogoutUser()
            userVm.responseLogout.observe(viewLifecycleOwner){
                if (it != null){
                    lifecycleScope.launch {
                        dataSotreUser.clear()
                    }
                    dataSotreUser.getToken.asLiveData().observe(viewLifecycleOwner){token ->
                        if (token.isNotEmpty()){
                            binding.layoutUserLogged.visibility = View.GONE
                            binding.layoutUserNotLogged.visibility = View.VISIBLE
                            Toast(requireContext()).showCustomToast(
                                "Logout akun sukses !", requireActivity(), R.layout.toast_alert_green)
                        }

                    }
                }else{
                    Toast(requireContext()).showCustomToast(
                        "Logout gagal !", requireActivity(), R.layout.toast_alert_red)
                }
            }
        }
    }


}