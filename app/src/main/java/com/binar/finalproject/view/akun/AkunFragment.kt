package com.binar.finalproject.view.akun

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
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
            val message: String? = "Are you sure you want to log out"
            showCustomDialogBox(message)
        }
    }

    private fun showCustomDialogBox(message: String?) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvMessage: TextView = dialog.findViewById(R.id.tvMessage)
        val btnYes: Button = dialog.findViewById(R.id.btnYes)
        val btnNo: Button = dialog.findViewById(R.id.btnNo)

        tvMessage.text = message
        tvMessage.text = message

        btnYes.setOnClickListener {
            userLogOut()
            dialog.dismiss()
        }

        btnNo.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()




    }

    private fun userLogOut() {
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