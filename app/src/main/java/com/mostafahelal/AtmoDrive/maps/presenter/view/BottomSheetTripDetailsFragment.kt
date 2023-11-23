package com.mostafahelal.AtmoDrive.maps.presenter.view

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mostafahelal.AtmoDrive.R
import com.mostafahelal.AtmoDrive.Utils.Constants
import com.mostafahelal.AtmoDrive.Utils.NetworkState
import com.mostafahelal.AtmoDrive.Utils.Resource
import com.mostafahelal.AtmoDrive.Utils.disable
import com.mostafahelal.AtmoDrive.Utils.enabled
import com.mostafahelal.AtmoDrive.Utils.showToast
import com.mostafahelal.AtmoDrive.Utils.visibilityGone
import com.mostafahelal.AtmoDrive.Utils.visibilityVisible
import com.mostafahelal.AtmoDrive.databinding.FragmentBottomSheetTripDetailsBinding
import com.mostafahelal.AtmoDrive.maps.domain.model.CancelTrip
import com.mostafahelal.AtmoDrive.maps.domain.model.CaptainDetails
import com.mostafahelal.AtmoDrive.maps.domain.model.DataCap
import com.mostafahelal.AtmoDrive.maps.domain.model.DataTrip
import com.mostafahelal.AtmoDrive.maps.domain.model.TripDetails
import com.mostafahelal.AtmoDrive.maps.presenter.viewmodel.SharedViewModel
import com.mostafahelal.AtmoDrive.maps.presenter.viewmodel.TripViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class BottomSheetTripDetailsFragment : Fragment() {
    lateinit var binding: FragmentBottomSheetTripDetailsBinding
    val viewModel: TripViewModel by viewModels()
    val sharedViewModel: SharedViewModel by activityViewModels()
    private var valueEventListener : ValueEventListener?= null
    private lateinit var database: DatabaseReference

    private var captainMobile = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentBottomSheetTripDetailsBinding.inflate(layoutInflater)
        return binding.root
      }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentBottomSheetTripDetailsBinding.bind(view)
        database = Firebase.database.reference
        viewModel.getCaptainDetails()
        viewModel.getTripDetails()

        listenerOnTrip()
        observer()
        onClick()
    }
    private fun onClick(){

        binding.imgCallCaptain.setOnClickListener {
            val phoneNumber = Uri.parse("tel:$captainMobile")
            val callIntent = Intent(Intent.ACTION_DIAL, phoneNumber)
            startActivity(callIntent)
        }

        binding.btnCancelTrip.setOnClickListener {
            viewModel.cancelTrip()
        }

    }
    private fun listenerOnTrip(){
        valueEventListener =  object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val tripStats = snapshot.getValue(String::class.java)

                when (tripStats) {
                    null -> {
                        sharedViewModel.setRequestTrip(false)
                    }
                    "accepted" -> {
                        binding.tvTripStatus.apply {
                            text = "Captain accepted."
                            setBackgroundColor(ContextCompat.getColor(context, R.color.progress))
                        }
                        binding.btnCancelTrip.apply {
                            enabled()
                            setBackgroundResource(R.drawable.background_cancel_trip)
                            setTextColor(ContextCompat.getColor(requireContext(), R.color.error))
                        }
                    }
                    "on_the_way" -> {
                        binding.tvTripStatus.apply {
                            text = "Captain is on the way to you."
                            setBackgroundColor(ContextCompat.getColor(context, R.color.progress))
                        }
                        binding.btnCancelTrip.apply {
                            enabled()
                            setBackgroundResource(R.drawable.background_cancel_trip)
                            setTextColor(ContextCompat.getColor(requireContext(), R.color.error))
                        }
                    }
                    "arrived" -> {
                        binding.tvTripStatus.apply {
                            text = "Captain is arrived to pickup."
                            setBackgroundColor(ContextCompat.getColor(context, R.color.success))
                        }
                        binding.btnCancelTrip.apply {
                            enabled()
                            setBackgroundResource(R.drawable.background_cancel_trip)
                            setTextColor(ContextCompat.getColor(requireContext(), R.color.error))
                        }
                    }
                    "start_trip" -> {
                        binding.tvTripStatus.apply {
                            text = "Trip running"
                            setBackgroundColor(ContextCompat.getColor(context, R.color.progress))
                        }
                        binding.btnCancelTrip.apply {
                            disable()
                            setBackgroundResource(R.drawable.background_disable)
                            setTextColor(Color.parseColor("#D6E2ED"))
                        }
                    }
                    "pay" -> {
                        binding.tvTripStatus.apply {
                            text = "You are arrived to drop off! 🎉🎉"
                            setBackgroundColor(ContextCompat.getColor(context, R.color.success))
                        }
                        binding.btnCancelTrip.apply {
                            disable()
                            setBackgroundResource(R.drawable.background_disable)
                            setTextColor(Color.parseColor("#D6E2ED"))
                        }
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        }

        database.child("trips").child(Constants.tripId.toString()).child("status")
            .addValueEventListener(valueEventListener!!)
    }
    private fun observer(){
        lifecycleScope.launch {
            viewModel.getCaptainDetailsResult.collect{ networkState ->
                when(networkState?.status){
                    NetworkState.Status.SUCCESS ->{
                        binding.tripCycleProgressBar.visibilityGone()
                        val data = networkState.data as Resource<CaptainDetails>
                        setCapUi(data.data?.cap!!)
                    }
                    NetworkState.Status.FAILED ->{
                        binding.tripCycleProgressBar.visibilityGone()
                        showToast(networkState.msg.toString())
                    }
                    NetworkState.Status.RUNNING ->{
                        binding.tripCycleProgressBar.visibilityVisible()
                    }
                    else -> {}
                }
            }
        }
        lifecycleScope.launch {
            viewModel.tripDetailsResult.collect{ networkState ->
                when(networkState?.status){
                    NetworkState.Status.SUCCESS ->{
                        binding.tripCycleProgressBar.visibilityGone()
                        val data = networkState.data as Resource<TripDetails>
                        updateCar(data.data?.tripData!!)
                    }
                    NetworkState.Status.FAILED ->{
                        binding.tripCycleProgressBar.visibilityGone()
                        showToast(networkState.msg.toString())
                    }
                    NetworkState.Status.RUNNING ->{
                        binding.tripCycleProgressBar.visibilityVisible()
                    }
                    else -> {}
                }
            }
        }
        lifecycleScope.launch {
            viewModel.cancelTripResult.collect{ networkState ->
                when(networkState?.status){
                    NetworkState.Status.SUCCESS ->{
                        binding.tripCycleProgressBar.visibilityGone()
                        val data = networkState.data as Resource<CancelTrip>
                        showToast(data.data?.message!!)
                    }
                    NetworkState.Status.FAILED ->{
                        binding.tripCycleProgressBar.visibilityGone()
                        showToast(networkState.msg.toString())
                    }
                    NetworkState.Status.RUNNING ->{
                        binding.tripCycleProgressBar.visibilityVisible()
                    }
                    else -> {}
                }
            }
        }
    }
    private fun setCapUi(data:DataCap){
        binding.apply {
            tvCaptainName.text = data.fullName
            tvTripCaptainPrice.text = data.estimateCost.toString()+" EGP"
        }
        captainMobile = data.mobile!!
        Glide.with(this)
            .load(Constants.BASE_Image_URL+data.avatar)
            .placeholder(R.drawable.img)
            .into(binding.imgTripCaptain)
    }

    private fun updateCar(data:DataTrip){
        binding.apply {
            textView6.text = data.car_brand
            textView10.text = data.car_model
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        database.child("trips").child(Constants.tripId.toString()).child("status")
            .removeEventListener(valueEventListener!!)
    }

}