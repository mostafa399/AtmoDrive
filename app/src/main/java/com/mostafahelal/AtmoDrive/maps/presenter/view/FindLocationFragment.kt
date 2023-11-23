package com.mostafahelal.AtmoDrive.maps.presenter.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mostafahelal.AtmoDrive.Utils.Constants
import com.mostafahelal.AtmoDrive.Utils.LocationState
import com.mostafahelal.AtmoDrive.Utils.NetworkState
import com.mostafahelal.AtmoDrive.Utils.Resource
import com.mostafahelal.AtmoDrive.Utils.showToast
import com.mostafahelal.AtmoDrive.Utils.visibilityGone
import com.mostafahelal.AtmoDrive.Utils.visibilityVisible
import com.mostafahelal.AtmoDrive.databinding.FragmentFindLocationBinding
import com.mostafahelal.AtmoDrive.maps.domain.model.MakeTrip
import com.mostafahelal.AtmoDrive.maps.presenter.viewmodel.SharedViewModel
import com.mostafahelal.AtmoDrive.maps.presenter.viewmodel.TripViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FindLocationFragment : Fragment() {
    lateinit var binding:FragmentFindLocationBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val viewModel:TripViewModel by viewModels()
    var locationState = LocationState.NONE
    var pickUpAddress: String? = null
    var dropOffAddress: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentFindLocationBinding.inflate(layoutInflater)
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
                         binding.locationTextView.text = sharedViewModel.getAddressFromLatLng(Constants.pickUpLatLng!!,requireContext())

                    }
                }
                LocationState.DROP_LOC -> {
                    if (Constants.dropOffLatLng != null) {
                        binding.tvWhereGo.text=sharedViewModel.getAddressFromLatLng(Constants.dropOffLatLng!!,requireContext())

                    }
                }
                LocationState.CANCEL -> {
                    binding.locationTextView.text = sharedViewModel.getAddressFromLatLng(Constants.pickUpLatLng!!,requireContext())
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
                viewModel.makeTrip()
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
                binding.locationTextView.text = sharedViewModel.getAddressFromLatLng(Constants.pickUpLatLng!!,requireContext())
                pickUpAddress = it.toString()
            }else if (locationState == LocationState.DROP_LOC && Constants.dropOffLatLng != null){
                binding.tvWhereGo.text = sharedViewModel.getAddressFromLatLng(Constants.dropOffLatLng!!,requireContext())
                dropOffAddress = it.toString()
            }else if (locationState == LocationState.CANCEL){
                binding.locationTextView.text = sharedViewModel.getAddressFromLatLng(Constants.pickUpLatLng!!,requireContext())
                binding.tvWhereGo.text = ""
            }

        })
    }

    private fun observeOnMakeTrip(){
        lifecycleScope.launch {
            viewModel.makeTripResult.collect{networkState->
                when(networkState?.status){
                    NetworkState.Status.SUCCESS -> {
                            binding.chooseLocProgressBar.visibilityGone()
                            val data = networkState.data as Resource<MakeTrip>
                            sharedViewModel.setMakeTripData(data.data?.data!!)
                            val action =
                                FindLocationFragmentDirections.actionFindLocationFragmentToBottomSheetRequestTripFragment()
                            findNavController().navigate(action)

                    }
                    NetworkState.Status.FAILED -> {
                        binding.chooseLocProgressBar.visibilityGone()

                        Log.d("FindLocationFragment", networkState.msg.toString())


                    }
                    NetworkState.Status.RUNNING -> {
                        binding.chooseLocProgressBar.visibilityVisible()

                    }
                    else -> Unit
                }

            }
        }
    }



}