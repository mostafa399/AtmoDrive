package com.mostafahelal.AtmoDrive.maps.presenter

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.model.LatLng
import com.mostafahelal.AtmoDrive.Utils.Constants
import com.mostafahelal.AtmoDrive.Utils.LocationState
import com.mostafahelal.AtmoDrive.Utils.showToast
import com.mostafahelal.AtmoDrive.databinding.FragmentFindLocationBinding
import java.io.IOException
import java.util.Locale

class FindLocationFragment : Fragment() {
    lateinit var binding:FragmentFindLocationBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    var locationState = LocationState.NONE
    var pickUpAddress: String? = null
    var dropOffAddress: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentFindLocationBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentFindLocationBinding.bind(view)
        handleSharedViewModel()
        handleClicks()



    }

    private fun handleSharedViewModel() {
        sharedViewModel.getLocation().observe(viewLifecycleOwner, Observer {
            when (locationState) {
                LocationState.PICKUP_LOC -> {
                    if (Constants.pickUpLatLng != null) {
                        binding.locationTextView.text = getAddressFromLatLng(Constants.pickUpLatLng!!)
                        pickUpAddress = it.toString()
                    }
                }
                LocationState.DROP_LOC -> {
                    if (Constants.dropOffLatLng != null) {
                        binding.tvWhereGo.text = getAddressFromLatLng(Constants.dropOffLatLng!!)
                        dropOffAddress = it.toString()
                    }
                }
                LocationState.CANCEL -> {
                    binding.locationTextView.text = getAddressFromLatLng(Constants.pickUpLatLng!!)
                    binding.tvWhereGo.text = ""
                }
                LocationState.NONE -> {
                    // Handle the NONE state if needed
                }

                else -> {}
            }
        })
    }
    private fun handleClicks() {
        binding.locationTextView.text = getAddressFromLatLng(Constants.pickUpLatLng!!)
        binding.locationTextView.setOnClickListener {
            locationState = LocationState.PICKUP_LOC
            sharedViewModel.setLocType(locationState.name)
        }
        binding.tvWhereGo.setOnClickListener {
            locationState = LocationState.DROP_LOC
            sharedViewModel.setLocType(locationState.name)
        }
        binding.confirm.setOnClickListener {
            val action = FindLocationFragmentDirections.actionFindLocationFragmentToBottomSheetRequestTripFragment()
            findNavController().navigate(action)
        }
    }

    private fun getAddressFromLatLng(latLng: LatLng): String {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (addresses?.isNotEmpty()!!) {
                val address = addresses[0]
                // You can format the address as per your requirements
                return address.getAddressLine(0)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            showToast("Error getting address")
        }
        return "Address not found"
    }


}