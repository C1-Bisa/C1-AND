package com.binar.finalproject.view.notifikasi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.binar.finalproject.R
import com.binar.finalproject.databinding.FragmentHomeBinding
import com.binar.finalproject.databinding.FragmentNotifikasiBinding
import com.binar.finalproject.model.Notifikasi
import com.binar.finalproject.view.adapter.AdapterNotifikasi
import com.binar.finalproject.view.adapter.SearchDestinationAdapter

class NotifikasiFragment : Fragment() {

    lateinit var binding: FragmentNotifikasiBinding
    private lateinit var notifikasiListAdapter: AdapterNotifikasi

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotifikasiBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayoutNotifikasi()
    }

    private fun setLayoutNotifikasi() {

        val listDataNotifikasi = listOf(
            Notifikasi("Promosi", "20 Maret 2023", "Dapatkan Potongan 50% Tiket!", "Syarat dan Ketentuan berlaku!"),
            Notifikasi("Promosi", "20 Maret 2023", "Dapatkan Potongan 50% Tiket!", "Syarat dan Ketentuan berlaku!")
        )

        notifikasiListAdapter = AdapterNotifikasi(listDataNotifikasi)

        binding.rvFavoriteDestination.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = notifikasiListAdapter
        }
    }


}