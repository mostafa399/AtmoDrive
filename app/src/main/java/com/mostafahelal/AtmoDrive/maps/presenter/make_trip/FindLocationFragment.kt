package com.mostafahelal.AtmoDrive.maps.presenter.make_trip

import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.model.LatLng
import com.mostafahelal.AtmoDrive.Utils.Constants
import com.mostafahelal.AtmoDrive.Utils.LocationState
import com.mostafahelal.AtmoDrive.Utils.NetworkState
import com.mostafahelal.AtmoDrive.Utils.Resource
import com.mostafahelal.AtmoDrive.Utils.showToast
import com.mostafahelal.AtmoDrive.Utils.visibilityGone
import com.mostafahelal.AtmoDrive.Utils.visibilityVisible
import com.mostafahelal.AtmoDrive.auth.presentation.view.LoginFragmentDirections
import com.mostafahelal.AtmoDrive.databinding.FragmentFindLocationBinding
import com.mostafahelal.AtmoDrive.maps.domain.model.MakeTrip
import com.mostafahelal.AtmoDrive.maps.presenter.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.Locale
@AndroidEntryPoint
class FindLocationFragment : Fragment() {
    lateinit var binding:FragmentFindLocationBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val viewModel:MakeTripViewModel by viewModels()
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
        observeOnLocation()



    }

    private fun handleSharedViewModel() {
        sharedViewModel.locTypeLiveData.observe(viewLifecycleOwner, Observer {
            when (locationState) {
                LocationState.PICKUP_LOC -> {
                    if (Constants.pickUpLatLng != null) {
                         binding.locationTextView.text = getAddressFromLatLng(Constants.pickUpLatLng!!)

                    }
                }
                LocationState.DROP_LOC -> {
                    if (Constants.dropOffLatLng != null) {
                        binding.tvWhereGo.text=getAddressFromLatLng(Constants.dropOffLatLng!!)

                    }
                }
                LocationState.CANCEL -> {
                    binding.locationTextView.text = getAddressFromLatLng(Constants.pickUpLatLng!!)
                    binding.tvWhereGo.text = ""
                }
                LocationState.NONE -> {
                    // Handle the NONE state if needed
                }
                LocationState.CONTINUE -> {

                }


                else -> {}
            }
        })
    }
    private fun handleClicks() {

        binding.locationTextView.setOnClickListener {
            locationState = LocationState.PICKUP_LOC
            sharedViewModel.setLocType(locationState.name)
        }
        binding.tvWhereGo.setOnClickListener {
            locationState = LocationState.DROP_LOC
            sharedViewModel.setLocType(locationState.name)
        }
        binding.confirm.setOnClickListener {
            if (pickUpAddress!=null&&dropOffAddress!=null){
                locationState = LocationState.CONTINUE
                sharedViewModel.setLocType(locationState.name)
                viewModel.makeTrip("500 KM",500,"160 Min",160)
                observeOnMakeTrip()
            }
            else{
                showToast("please confirm you have selected two locations")
            }
        }


    }
    private fun observeOnLocation(){
        sharedViewModel.getLocation().observe(viewLifecycleOwner, Observer {

            if (locationState == LocationState.PICKUP_LOC && Constants.pickUpLatLng != null){
                binding.locationTextView.text = getAddressFromLatLng(Constants.pickUpLatLng!!)
                pickUpAddress = it.toString()
            }else if (locationState == LocationState.DROP_LOC && Constants.dropOffLatLng != null){
                binding.tvWhereGo.text = getAddressFromLatLng(Constants.dropOffLatLng!!)
                dropOffAddress = it.toString()
            }else if (locationState == LocationState.CANCEL){
                binding.locationTextView.text = getAddressFromLatLng(Constants.pickUpLatLng!!)
                binding.tvWhereGo.text = ""
            }

        })
    }

    private fun getAddressFromLatLng(latLng: LatLng): String {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (addresses?.isNotEmpty()!!) {
                val address = addresses[0]
                return address.getAddressLine(0)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            showToast("Error getting address")
        }
        return "Address not found"
    }
    private fun observeOnMakeTrip(){
        lifecycleScope.launch {

            viewModel.makeTripResult.collect{networkState->
                when(networkState?.status){
                    NetworkState.Status.SUCCESS -> {
                        withContext(Dispatchers.Main) {
                            val action = FindLocationFragmentDirections.actionFindLocationFragmentToBottomSheetRequestTripFragment()
                            findNavController().navigate(action)
                        }

                    }
                    NetworkState.Status.FAILED -> {
                        Log.d("LoginFragment", networkState.msg.toString())


                    }
                    NetworkState.Status.RUNNING -> {
                    }
                    else -> Unit
                }

            }
        }
    }



}