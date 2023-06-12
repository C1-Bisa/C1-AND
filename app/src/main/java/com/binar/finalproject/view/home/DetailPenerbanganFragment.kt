package com.binar.finalproject.view.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.binar.finalproject.R
import com.binar.finalproject.databinding.DialogTicketSoldOutBinding
import com.binar.finalproject.databinding.FragmentDetailPenerbanganBinding
import com.binar.finalproject.databinding.SeatclassDialogLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class DetailPenerbanganFragment : Fragment() {

    private lateinit var binding : FragmentDetailPenerbanganBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailPenerbanganBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSelectFlight.setOnClickListener {
//            jika ticket habis show dialog
            showDialogTecketSoldOut()
        }
    }

    private fun showDialogTecketSoldOut() {
        val dialog = BottomSheetDialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_ticket_sold_out)
        val bindingDialog = DialogTicketSoldOutBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialog.root)

        bindingDialog.btnClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation;
        dialog.window?.setGravity(Gravity.BOTTOM);
    }

}