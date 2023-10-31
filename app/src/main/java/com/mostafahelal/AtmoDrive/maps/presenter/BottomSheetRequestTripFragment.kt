package com.mostafahelal.AtmoDrive.maps.presenter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mostafahelal.AtmoDrive.databinding.FragmentBottomSheetRequestTripBinding
import com.mostafahelal.AtmoDrive.maps.BottomSheetRequestTripFragmentDirections

class BottomSheetRequestTripFragment : Fragment() {
    lateinit var binding:FragmentBottomSheetRequestTripBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentBottomSheetRequestTripBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentBottomSheetRequestTripBinding.bind(view)

//        val bottomSheetView = view.findViewById<View>(R.id.bottom_sheet_request_trip)
//        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView)
//        val yourView = view.findViewById<View>(R.id.drag_handle)
//
//        yourView.setOnClickListener {
//            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//        }

//        val bottomSheet =view.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
//        binding.dragHandle.setOnClickListener {
//                val behavior = BottomSheetBehavior.from(bottomSheet)
//                behavior.state = BottomSheetBehavior.STATE_EXPANDED
//                behavior.peekHeight = resources.displayMetrics.heightPixels // Set peek height
//        }
        binding.btnRequsetTrip.setOnClickListener {
            val action=
                BottomSheetRequestTripFragmentDirections.actionBottomSheetRequestTripFragmentToBottomSheetTripDetailsFragment()
            findNavController().navigate(action)

        }

    }
}