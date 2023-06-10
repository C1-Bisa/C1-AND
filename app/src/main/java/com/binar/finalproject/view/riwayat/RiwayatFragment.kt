package com.binar.finalproject.view.riwayat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.finalproject.R
import com.binar.finalproject.databinding.FragmentNotifikasiBinding
import com.binar.finalproject.databinding.FragmentRiwayatBinding
import com.binar.finalproject.model.RiwayatModel
import com.binar.finalproject.view.adapter.AdapterRiwayat

class RiwayatFragment : Fragment() {

    lateinit var binding : FragmentRiwayatBinding

    private lateinit var riwayatListData : AdapterRiwayat
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRiwayatBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayoutListData()
    }

    private fun setLayoutListData() {
        val listDataRiwayattt  = listOf(
            RiwayatModel("Unpaid", "Jakarta", "5 Maret 2023", "19:30",
                "Booking Code:", 453716288, "Class: ", "Economy", "IDR 5.950.000", "4h 0m")
        )
        riwayatListData = AdapterRiwayat(listDataRiwayattt)

        binding.rvRiwayat.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = riwayatListData
        }

    }


}