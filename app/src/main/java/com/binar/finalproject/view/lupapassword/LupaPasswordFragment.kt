package com.binar.finalproject.view.lupapassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.binar.finalproject.R
import com.binar.finalproject.databinding.FragmentLupaPasswordBinding
import com.binar.finalproject.databinding.FragmentOtpBinding

class LupaPasswordFragment : Fragment() {

    lateinit var binding : FragmentLupaPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLupaPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}