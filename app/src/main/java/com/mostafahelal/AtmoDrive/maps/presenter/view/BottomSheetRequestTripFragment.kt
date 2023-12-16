package com.mostafahelal.AtmoDrive.maps.presenter.view

import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.gms.maps.model.LatLng
import com.mostafahelal.AtmoDrive.R
import com.mostafahelal.AtmoDrive.Utils.Constants
import com.mostafahelal.AtmoDrive.Utils.LocationState
import com.mostafahelal.AtmoDrive.Utils.NetworkState
import com.mostafahelal.AtmoDrive.Utils.Resource
import com.mostafahelal.AtmoDrive.Utils.showToast
import com.mostafahelal.AtmoDrive.Utils.visibilityGone
import com.mostafahelal.AtmoDrive.Utils.visibilityVisible
import com.mostafahelal.AtmoDrive.databinding.FragmentBottomSheetRequestTripBinding
import com.mostafahelal.AtmoDrive.maps.domain.model.ConfirmTrip
import com.mostafahelal.AtmoDrive.maps.presenter.viewmodel.SharedViewModel
import com.mostafahelal.AtmoDrive.maps.presenter.viewmodel.TripViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Locale

@AndroidEntryPoint
class BottomSheetRequestTripFragment : Fragment() {
    lateinit var binding:FragmentBottomSheetRequestTripBinding
    val viewModel: TripViewModel by viewModels()
    val sharedViewModel: SharedViewModel by activityViewModels()
    private var estimateCost: String = ""
    private var estimateTime: String = ""
    private var estimateDistance: String = ""
    var tripId:String?=null


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
        binding.close.setOnClickListener {
            sharedViewModel.setLocType(LocationState.CANCEL.name)
        }
        observeOnTripData()
        observeOnConfirmTrip()
        onClick()

    }
    private fun observeOnTripData(){
        sharedViewModel.getMakeTripData().observe(viewLifecycleOwner, Observer {
            estimateCost = it.estimate_cost
            estimateTime = it.estimate_time.toString()
            estimateDistance = it.estimate_distance.toString()
            binding.textView2.text = it.vehicle_class_name
            binding.cost.text = "$estimateCost EGP"
            binding.time.text = "$estimateTime mins way from you"
            Glide.with(this)
                .load(Constants.BASE_Image_URL+it.vehicle_class_icon)
                .placeholder(R.drawable.atmo_plus)
                .into(binding.imageView)
        })
    }
    private fun observeOnConfirmTrip(){
        lifecycleScope.launch {
            viewModel.confirmTrip.collect{ networkState->
                when(networkState?.status){
                    NetworkState.Status.SUCCESS->{
                            binding.requestTripProgressBar.visibilityGone()
                            val data = networkState.data as Resource<ConfirmTrip>
                            Constants.tripId = data.data?.tripId!!
                            sharedViewModel.setRequestTrip(true) }

                    NetworkState.Status.FAILED->{
                        binding.requestTripProgressBar.visibilityGone()
                        Log.d("ConfirmTrip", networkState.msg.toString())

                    }
                    NetworkState.Status.RUNNING->{
                        binding.requestTripProgressBar.visibilityVisible()

                    }

                    else -> Unit
                }
            }
        }
    }
    private fun onClick(){
        binding.btnRequsetTrip.setOnClickListener {
            val pickUpLat = Constants.pickUpLatLng!!.latitude.toString()
            val pickUpLng = Constants.pickUpLatLng!!.longitude.toString()
            val dropOffLat = Constants.dropOffLatLng!!.latitude.toString()
            val dropOffLng = Constants.dropOffLatLng!!.longitude.toString()
            val pickUpName = getAddressFromLatLng(Constants.pickUpLatLng!!)
            val dropOffName = getAddressFromLatLng(Constants.dropOffLatLng!!)
            viewModel.confirmTrip("1",pickUpLat,pickUpLng,dropOffLat,dropOffLng,estimateCost,estimateTime,estimateDistance,pickUpName,dropOffName)
        }
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





}