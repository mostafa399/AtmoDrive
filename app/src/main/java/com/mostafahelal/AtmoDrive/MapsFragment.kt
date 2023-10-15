package com.mostafahelal.AtmoDrive

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.visibilityGone
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.visibilityVisible
import com.mostafahelal.AtmoDrive.databinding.FragmentMaps2Binding
import com.mostafahelal.AtmoDrive.maps.ItemModel
import com.mostafahelal.AtmoDrive.maps.MyAdapter


class MapsFragment : Fragment(),OnMapReadyCallback {
    private lateinit var binding:FragmentMaps2Binding
    private val call = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            requireActivity().finish()
        }

    }
    private lateinit var mMap: GoogleMap
    private var mapFragment:SupportMapFragment?=null
    private var mLocationRequest: LocationRequest?=null
    private var mLocationCallback: LocationCallback?=null
    private var mFusedLocationProviderClient: FusedLocationProviderClient?=null
    private lateinit var textView: TextView
    private lateinit var lay1:LinearLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentMaps2Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentMaps2Binding.bind(view)
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment!!.getMapAsync(this)
        initLocation()

        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.find_location_buttom_sheet)
        val recyclerView=bottomSheetDialog.findViewById<RecyclerView>(R.id.recyclerView)
        textView= bottomSheetDialog.findViewById<TextView>(R.id.locationTextView)!!
        lay1= bottomSheetDialog.findViewById<LinearLayout>(R.id.lay1)!!
        recyclerView?.layoutManager = LinearLayoutManager(context)
        val itemList = listOf(
            ItemModel(R.drawable.locationmap1, "F. 5606 OWest wahat road", "6 October city"),
            ItemModel(R.drawable.locationmap1, "F. 5606 OWest wahat road", "6 October city"),
            ItemModel(R.drawable.locationmap1, "F. 5606 OWest wahat road", "6 October city"),
            ItemModel(R.drawable.locationmap1, "F. 5606 OWest wahat road", "6 October city"),
            ItemModel(R.drawable.locationmap1, "F. 5606 OWest wahat road", "6 October city"),
        )
        val adapter = MyAdapter(itemList)
        recyclerView?.adapter = adapter

        binding.tvWhereGo.setOnClickListener{
            bottomSheetDialog.show()

        }
        lay1.setOnClickListener{
            binding.tripCycleCard.visibilityVisible()
            bottomSheetDialog.dismiss()
            binding.locationCard.visibilityGone()
        }
    }
    private fun initLocation(){
        if (mFusedLocationProviderClient==null){
            mFusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(requireActivity())
        }
        if (mLocationRequest==null){
            mLocationRequest=LocationRequest.create()
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .setInterval(2000)
                .setFastestInterval(2000)
                .setSmallestDisplacement(5f)
        }
    }

    //firstStep CheckPermission
    private fun checkPermission(){
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)!=
            PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)!=
            PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf( Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION),2)

        }else{
            locationChecker()
        }

    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        mMap.isMyLocationEnabled=true
        mMap.uiSettings.isMyLocationButtonEnabled=true
        //location button
        val locationButton=(mapFragment!!.requireView().findViewById<View>("1".toInt())?.parent as View)
            .findViewById<View>("2".toInt())
        locationButton.setBackgroundColor(resources.getColor(R.color.primary))
        val params=locationButton.layoutParams as RelativeLayout.LayoutParams
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP,0)
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        params.bottomMargin=250
        params.marginEnd=50
        mLocationCallback=object :LocationCallback(){
            override fun onLocationResult(result: LocationResult) {
                //posttofirebase && add carimage
                var location= LatLng(result.lastLocation!!.latitude,result.lastLocation!!.longitude)
                moveCameraMap(location)
                binding.tvYourLocation.text="${location.latitude}/${location.longitude}"

                textView?.text ="${location.latitude}/${location.longitude}"

            }
        }
        mFusedLocationProviderClient?.requestLocationUpdates(mLocationRequest!!,mLocationCallback!!,
            Looper.getMainLooper())

    }

    private fun moveCameraMap(latLng: LatLng){
        val cameraPos= CameraPosition.builder().target(latLng).zoom(18f).build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPos))
    }

    private fun locationChecker(){
        val builder= LocationSettingsRequest.Builder()
            .addLocationRequest(mLocationRequest!!)
        val result: Task<LocationSettingsResponse> = LocationServices
            .getSettingsClient(requireActivity()).checkLocationSettings(builder.build())
        result.addOnCompleteListener{task->
            try {
                task.getResult(ApiException::class.java)
                getLocation()
            }catch (e: ApiException){
                when(e.statusCode){
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED->{
                        try {
                            val resolver=e as ResolvableApiException
                            startIntentSenderForResult(resolver.resolution.intentSender, Priority.PRIORITY_HIGH_ACCURACY
                                ,null,0,0,0,null)

                        }catch (e:Exception){

                        }
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE->{

                    }
                }

            }



        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            Priority.PRIORITY_HIGH_ACCURACY->{
                when(resultCode){
                    Activity.RESULT_OK->{
                        getLocation()
                    }
                    Activity.RESULT_CANCELED->{
                        locationChecker()
                    }
                }
            }
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.apply {
//            mMap.isMyLocationEnabled=true
        }
        checkPermission()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==2&&permissions.isNotEmpty()&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
            locationChecker()
        }
    }
}