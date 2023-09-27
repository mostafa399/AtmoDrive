package com.mostafahelal.AtmoDrive.maps

import com.mostafahelal.AtmoDrive.maps.ItemModel
import com.mostafahelal.AtmoDrive.maps.MyAdapter
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
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
import com.mostafahelal.AtmoDrive.R
import com.mostafahelal.AtmoDrive.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var _binding: ActivityMapsBinding?=null
    private val binding get() = _binding!!
    private var mapFragment:SupportMapFragment?=null
    private var mLocationRequest:LocationRequest?=null
    private var mLocationCallback:LocationCallback?=null
    private var mFusedLocationProviderClient:FusedLocationProviderClient?=null
    private lateinit var textView:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment!!.getMapAsync(this)
        initLocation()

        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.find_location_buttom_sheet)
        val recyclerView=bottomSheetDialog.findViewById<RecyclerView>(R.id.recyclerView)
        textView= bottomSheetDialog.findViewById<TextView>(R.id.locationTextView)!!
        recyclerView?.layoutManager = LinearLayoutManager(applicationContext)
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
    }
    private fun initLocation(){
        if (mFusedLocationProviderClient==null){
            mFusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)
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
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=
            PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=
            PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf( Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION),2)

        }else{
            locationChecker()
        }

    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
       mMap.isMyLocationEnabled=true
        mLocationCallback=object :LocationCallback(){
            override fun onLocationResult(result: LocationResult) {
                //posttofirebase && add carimage
                var location=LatLng(result.lastLocation!!.latitude,result.lastLocation!!.longitude)
                moveCameraMap(location)
                binding.tvYourLocation.text="${location.latitude}/${location.longitude}"

                textView?.text ="${location.latitude}/${location.longitude}"

            }
        }
        mFusedLocationProviderClient?.requestLocationUpdates(mLocationRequest!!,mLocationCallback!!,
            Looper.getMainLooper())

    }

    private fun moveCameraMap(latLng: LatLng){
        val cameraPos=CameraPosition.builder().target(latLng).zoom(18f).build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPos))
    }

    private fun locationChecker(){
        val builder=LocationSettingsRequest.Builder()
            .addLocationRequest(mLocationRequest!!)
        val result:Task<LocationSettingsResponse> = LocationServices
            .getSettingsClient(this).checkLocationSettings(builder.build())
        result.addOnCompleteListener{task->
            try {
                task.getResult(ApiException::class.java)
                getLocation()
            }catch (e:ApiException){
                when(e.statusCode){
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED->{
                        try {
                            val resolver=e as ResolvableApiException
                            startIntentSenderForResult(resolver.resolution.intentSender,Priority.PRIORITY_HIGH_ACCURACY
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
        if (requestCode==2&&permissions.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            locationChecker()
        }
    }
}