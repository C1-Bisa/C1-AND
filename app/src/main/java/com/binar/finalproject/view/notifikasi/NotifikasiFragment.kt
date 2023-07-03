package com.binar.finalproject.view.notifikasi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.binar.finalproject.R
import com.binar.finalproject.databinding.FragmentHomeBinding
import com.binar.finalproject.databinding.FragmentNotifikasiBinding
import com.binar.finalproject.local.DataStoreUser
import com.binar.finalproject.model.Notifikasi
import com.binar.finalproject.model.notification.responsegetnotif.ResponseDataNotification
import com.binar.finalproject.utils.showCustomToast
import com.binar.finalproject.view.adapter.AdapterNotifikasi
import com.binar.finalproject.view.adapter.SearchDestinationAdapter
import com.binar.finalproject.viewmodel.NotificationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotifikasiFragment : Fragment() {

    private lateinit var binding: FragmentNotifikasiBinding
    private lateinit var notifikasiListAdapter: AdapterNotifikasi
    private val notificationViewModel: NotificationViewModel by viewModels()
    private lateinit var dataUserStoreUser: DataStoreUser

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

        dataUserStoreUser = DataStoreUser(requireContext().applicationContext)

        //untuk meload data
        binding.layoutLoadingData.visibility = View.VISIBLE
        binding.rvFavoriteDestination.visibility = View.GONE

        dataUserStoreUser.getToken.asLiveData().observe(viewLifecycleOwner){
            if(it != null){
                setLayoutNotifikasi(it)
            }

        }

        binding.btnMarkAllRead.setOnClickListener {
            markAllNotif()
        }

    }

    private fun markAllNotif() {
        var token : String? = ""
        dataUserStoreUser.getToken.asLiveData().observe(viewLifecycleOwner){
            if(it != null){
                token = it
                notificationViewModel.updateNotification(it)
            }
        }

        notificationViewModel.responseUpdateNotif.observe(viewLifecycleOwner){
            if(it != null){
                if(token != null){
                    Toast(requireContext()).showCustomToast(
                        "Menandai semua notifikasi berhasil ditandai", requireActivity(), R.layout.toast_alert_green)
                    setLayoutNotifikasi(token.toString())
                }
            }
        }
    }

    private fun setLayoutNotifikasi(token : String) {

        notificationViewModel.getNotification(token)
        notifikasiListAdapter = AdapterNotifikasi(emptyList())

        binding.rvFavoriteDestination.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = notifikasiListAdapter

        }



        notificationViewModel.responseNotif.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                notifikasiListAdapter.setListNotif(it.sortedByDescending {data->
                    data.createdAt
                })
                binding.layoutLoadingData.visibility = View.GONE
                binding.rvFavoriteDestination.visibility = View.VISIBLE
            }else{
                binding.layoutLoadingData.visibility = View.GONE
                binding.rvFavoriteDestination.visibility = View.VISIBLE
            }
        }


    }


}