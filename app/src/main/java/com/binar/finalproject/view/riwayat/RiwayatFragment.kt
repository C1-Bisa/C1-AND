package com.binar.finalproject.view.riwayat

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.finalproject.R
import com.binar.finalproject.databinding.DateDialogLayoutBinding
import com.binar.finalproject.databinding.DateDialogRiwayatBinding
import com.binar.finalproject.databinding.FragmentNotifikasiBinding
import com.binar.finalproject.databinding.FragmentRiwayatBinding
import com.binar.finalproject.databinding.SearchDialogLayoutBinding
import com.binar.finalproject.databinding.SearchDialogRiwayatBinding
import com.binar.finalproject.local.DataStoreUser
import com.binar.finalproject.model.RiwayatModel
import com.binar.finalproject.view.adapter.AdapterRiwayat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.timessquare.CalendarPickerView
import java.util.*

class RiwayatFragment : Fragment() {

    lateinit var binding : FragmentRiwayatBinding

    private lateinit var riwayatListData : AdapterRiwayat

    private lateinit var dataSotreUser : DataStoreUser
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

        binding.btnSearchRiwayat.setOnClickListener {
            showDialogSearch()
        }

        binding.btnFilterRiwayatAfterData.setOnClickListener {
            showDialogFilterDateRiwayat()
        }

        dataSotreUser = DataStoreUser(requireContext().applicationContext)

        if (dataSotreUser.isAlreadyLogin()) {
            binding.layoutNonLogin.visibility = View.GONE
            binding.linearLayoutLogin.visibility = View.VISIBLE
        } else {
            binding.layoutNonLogin.visibility = View.VISIBLE
            binding.linearLayoutLogin.visibility = View.GONE

        }

        binding.btnMasukRiwayat.setOnClickListener {
            findNavController().navigate(R.id.action_riwayatFragment_to_loginFragment)
        }
    }

    private fun showDialogFilterDateRiwayat() {
        val dialogFilterDate = BottomSheetDialog(requireContext())

        dialogFilterDate.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogFilterDate.setContentView(R.layout.date_dialog_riwayat)
        val bindingDialogFilter = DateDialogRiwayatBinding.inflate(layoutInflater)
        dialogFilterDate.setContentView(bindingDialogFilter.root)

        setInitialDatePicker(bindingDialogFilter)
        bindingDialogFilter.btnClose.setOnClickListener {
            dialogFilterDate.dismiss()
        }

        dialogFilterDate.show()
        dialogFilterDate.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogFilterDate.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialogFilterDate.window?.attributes?.windowAnimations = R.style.DialogAnimation;
        dialogFilterDate.window?.setGravity(Gravity.BOTTOM);
    }

    private fun setInitialDatePicker(bindingDialogFilter : DateDialogRiwayatBinding) {
        val startDate = Date()

        val nextYear = Calendar.getInstance()

        nextYear.add(Calendar.YEAR, 1)

        bindingDialogFilter.dateRiwayat.init(startDate,nextYear.time)
            .inMode(CalendarPickerView.SelectionMode.RANGE)
            .withSelectedDate(startDate)
    }

    private fun showDialogSearch() {
        val dialogSearchRiwayat = Dialog(requireContext())

        dialogSearchRiwayat.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogSearchRiwayat.setContentView(R.layout.search_dialog_riwayat)
        val bindingSearchRiwayat = SearchDialogRiwayatBinding.inflate(layoutInflater)
        dialogSearchRiwayat.setContentView(bindingSearchRiwayat.root)


        //menutup dialog
        bindingSearchRiwayat.btnCloseRiwayat.setOnClickListener {
            dialogSearchRiwayat.dismiss()
        }

        dialogSearchRiwayat.show()
        dialogSearchRiwayat.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialogSearchRiwayat.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialogSearchRiwayat.window?.attributes?.windowAnimations = R.style.DialogAnimation;
        dialogSearchRiwayat.window?.setGravity(Gravity.BOTTOM);
    }

    private fun setLayoutListData() {
        val listDataRiwayattt  = listOf(
            RiwayatModel("Unpaid", "Jakarta", "5 Maret 2023", "19:30",
                "Booking Code:", 453716288, "Class: ", "Economy", "IDR 5.950.000", "4h 0m")
        )
        riwayatListData = AdapterRiwayat(listDataRiwayattt)

        binding.rvRiwayatAfterData.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = riwayatListData
        }

    }


}