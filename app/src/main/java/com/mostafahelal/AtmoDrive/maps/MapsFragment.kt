package com.mostafahelal.AtmoDrive.maps

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mostafahelal.AtmoDrive.R
import com.mostafahelal.AtmoDrive.auth.presentation.view_model.CheckCodeViewModel
import com.mostafahelal.AtmoDrive.databinding.FragmentMapsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsFragment : Fragment() {
    private val call=object :OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            requireActivity().finish()
        }

    }
    var _binding:FragmentMapsBinding?=null
    val binding get() =_binding
    var mapsFragment: SupportMapFragment?=null


    private val callback = OnMapReadyCallback { googleMap ->
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding= FragmentMapsBinding.inflate(layoutInflater)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,call)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapsFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapsFragment?.getMapAsync(callback)
    }
}