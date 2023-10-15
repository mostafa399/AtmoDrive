package com.mostafahelal.AtmoDrive

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mostafahelal.AtmoDrive.databinding.FragmentBottomSheetRequestTripBinding

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
        binding.btnRequsetTrip.setOnClickListener {
            val action=BottomSheetRequestTripFragmentDirections.actionBottomSheetRequestTripFragmentToBottomSheetTripDetailsFragment()
            findNavController().navigate(action)

        }

    }
}