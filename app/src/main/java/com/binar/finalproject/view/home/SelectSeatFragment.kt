package com.binar.finalproject.view.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.binar.finalproject.R
import com.binar.finalproject.databinding.FragmentSelectSeatBinding
import com.binar.finalproject.model.seatconfiguration.Seat
import com.binar.finalproject.view.adapter.SeatAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectSeatFragment : Fragment() {

    private lateinit var binding: FragmentSelectSeatBinding
    private lateinit var seatAdapter: SeatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSelectSeatBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.visibility = View.GONE

        seatAdapter = SeatAdapter(emptyList(), 3)
        binding.rvSeat.apply {
            layoutManager = GridLayoutManager(context, 7)
            adapter = seatAdapter
        }

        seatAdapter.setListSeat(setListSeat())

        seatAdapter.itemSeatOnClick = {
            Log.i("HASIL_SEAT", it.seatCode)
        }

        binding.btnSimpanSeat.setOnClickListener {
            Log.i("HASIL_SEAT", seatAdapter.getConfigurationSeatPass().toString())
        }
    }

    private fun setListSeat() : List<Seat>{
        return listOf(
            Seat("A1", true) ,
            Seat("B1", false),
            Seat("C1", true),
            Seat("1", true),
            Seat("D1", true),
            Seat("E1", false),
            Seat("F1", true),
            Seat("A2", true) ,
            Seat("B2", false),
            Seat("C2", false),
            Seat("2", true),
            Seat("D2", true),
            Seat("E2", false),
            Seat("F2", true),
            Seat("A3", false) ,
            Seat("B3", false),
            Seat("C3", true),
            Seat("3", true),
            Seat("D3", false),
            Seat("E3", true),
            Seat("F3", false),
            Seat("A4", true) ,
            Seat("B4", false),
            Seat("C4", false),
            Seat("4", true),
            Seat("D4", true),
            Seat("E4", false),
            Seat("F4", false),
            Seat("A5", true) ,
            Seat("B5", false),
            Seat("C5", true),
            Seat("5", true),
            Seat("D5", true),
            Seat("E5", false),
            Seat("F5", false),
            Seat("A6", true) ,
            Seat("B6", false),
            Seat("C6", true),
            Seat("6", true),
            Seat("D6", true),
            Seat("E6", false),
            Seat("F6", true),
            Seat("A7", false) ,
            Seat("B7", false),
            Seat("C7", false),
            Seat("7", true),
            Seat("D7", false),
            Seat("E7", false),
            Seat("F7", true),
            Seat("A8", true) ,
            Seat("B8", false),
            Seat("C8", true),
            Seat("8", true),
            Seat("D8", true),
            Seat("E8", false),
            Seat("F8", true),
            Seat("A9", true) ,
            Seat("B9", false),
            Seat("C9", true),
            Seat("9", true),
            Seat("D9", false),
            Seat("E9", false),
            Seat("F9", true),
            Seat("A10", true) ,
            Seat("B10", true),
            Seat("C10", true),
            Seat("10", true),
            Seat("D10", false),
            Seat("E10", true),
            Seat("F10", true),
            Seat("A11", true) ,
            Seat("B11", false),
            Seat("C11", true),
            Seat("11", true),
            Seat("D11", true),
            Seat("E11", false),
            Seat("F11", true),
            Seat("A12", true) ,
            Seat("B12", true),
            Seat("C12", true),
            Seat("12", true),
            Seat("D12", false),
            Seat("E12", true),
            Seat("F12", true)
        )
    }

}