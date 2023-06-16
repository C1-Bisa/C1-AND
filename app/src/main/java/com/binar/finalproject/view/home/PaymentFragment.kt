package com.binar.finalproject.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.binar.finalproject.R
import com.binar.finalproject.databinding.FragmentPaymentBinding

class PaymentFragment : Fragment() {

    private lateinit var binding : FragmentPaymentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPaymentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnExpandCreditCard.setOnClickListener {
            if(binding.layoutCreditCard.isVisible){
                binding.layoutCreditCard.visibility = View.GONE
            }else{
                binding.layoutCreditCard.visibility = View.VISIBLE
            }
        }
    }

}