package com.mostafahelal.AtmoDrive.maps.presenter

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Geocoder
import android.os.Bundle
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mostafahelal.AtmoDrive.R
import com.mostafahelal.AtmoDrive.Utils.Constants
import com.mostafahelal.AtmoDrive.Utils.LocationState
import com.mostafahelal.AtmoDrive.Utils.LockableBottomSheetBehavior
import com.mostafahelal.AtmoDrive.Utils.MapUtils
import com.mostafahelal.AtmoDrive.Utils.showToast
import com.mostafahelal.AtmoDrive.Utils.visibilityGone
import com.mostafahelal.AtmoDrive.Utils.visibilityVisible
import com.mostafahelal.AtmoDrive.databinding.FragmentMaps2Binding
import com.mostafahelal.AtmoDrive.maps.presenter.make_trip.FindLocationFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.Locale

class MapsFragment : Fragment(),OnMapReadyCallback {

    private lateinit var binding: FragmentMaps2Binding
    //sharedViewModel initialization
    private val sharedViewModel: SharedViewModel by activityViewModels()
    //init maps
    private lateinit var mMap: GoogleMap
    private var mapFragment: SupportMapFragment? = null
    //init location
    private var mLocationRequest: LocationRequest? = null
    private var mLocationCallback: LocationCallback? = null
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    //init bottom sheet
    private var bottomSheetView: ConstraintLayout? = null
    private var sheet: BottomSheetBehavior<ConstraintLayout> = LockableBottomSheetBehavior()
    private var myNavHostFragment: NavHostFragment? = null
    //add two markers on Map
    private var pickUpMarker: Marker? = null
    private var dropOffMarker: Marker? = null
    private var address=""
    private var myLoc: LatLng? = null
    private var pickUpOrDropOf = 0

    private lateinit var database: DatabaseReference



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? {
        binding = FragmentMaps2Binding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMaps2Binding.bind(view)
        init()
        initBottomSheets()
        handleBottomSheetSize()
        onBackPressHandle()
        dealWithSharedViewModel()
        onClicks()

    }

    private fun init() {
        //initMap
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment!!.getMapAsync(this)
        //init nav host that have bottom sheet
        myNavHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment11) as NavHostFragment
        database = FirebaseDatabase.getInstance().getReference()
        if (mFusedLocationProviderClient == null) {
            mFusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(requireActivity())
        }
        if (mLocationRequest == null) {
            mLocationRequest = LocationRequest.create()
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .setInterval(5000)
                .setFastestInterval(5000)
                .setSmallestDisplacement(10f)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        mMap.isMyLocationEnabled = true
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val latLng = LatLng(result.lastLocation!!.latitude,result.lastLocation!!.longitude)
                moveCameraMap(latLng)
                myLoc = latLng
                address=getAddressFromLatLng(latLng)
                binding.tvYourLocation.text=address

            }
        }
        mFusedLocationProviderClient?.requestLocationUpdates(
            mLocationRequest!!, mLocationCallback!!,
            Looper.getMainLooper()
        )

    }
    private fun moveCameraMap(latLng: LatLng) {
        val cameraPos = CameraPosition.builder().target(latLng).zoom(18f).build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPos))
    }
    private fun locationChecker() {
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(mLocationRequest!!)
        val result: Task<LocationSettingsResponse> = LocationServices
            .getSettingsClient(requireActivity()).checkLocationSettings(builder.build())
        result.addOnCompleteListener { task ->
            try {
                task.getResult(ApiException::class.java)
                getLocation()

            } catch (e: ApiException) {
                when (e.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        try {
                            val resolver = e as ResolvableApiException
                            startIntentSenderForResult(
                                resolver.resolution.intentSender,
                                Priority.PRIORITY_HIGH_ACCURACY,
                                null,
                                0,
                                0,
                                0,
                                null
                            )

                        } catch (e: Exception) {

                        }
                    }

                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                    } } } } }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Priority.PRIORITY_HIGH_ACCURACY -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        getLocation()
                    }

                    Activity.RESULT_CANCELED -> {
                        locationChecker()
                    }
                }
            }
        }
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isMyLocationButtonEnabled = false
        if (resources.getString(R.string.mode) == "Night") {
            setUpDarkTheme(mMap)
        }
        mMap.setOnCameraIdleListener {
            setLocation(false)
            val loc = mMap.cameraPosition.target
            address = getAddressFromLatLng(loc)
            if(pickUpOrDropOf == 1){
                Constants.pickUpLatLng = loc
            }
            else if (pickUpOrDropOf == 2){
                Constants.dropOffLatLng = loc

            }

        }

        checkPermission()
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 2 && permissions.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            locationChecker()
        }
    }
    private fun onClicks() {
        // CardView
        binding.locationCard.setOnClickListener {
            Constants.pickUpLatLng=myLoc
            binding.imageButton.visibilityGone()
            binding.locationCard.visibilityGone()
            showBottomSheet()

            // Check if it's a pickup or drop-off
            if (pickUpOrDropOf == 0) {
                Constants.pickUpLatLng = myLoc!!
                binding.tvYourLocation.text = address
            }
        }

        binding.btnCancelTrip.setOnClickListener {
            binding.imgLocationMarker.visibilityGone()
            binding.btnChooseLocation.visibilityGone()
            binding.btnCancelTrip.visibilityGone()
            sheet.state = BottomSheetBehavior.STATE_EXPANDED
            Constants.isBottomSheetOn = true
        }

        binding.imageButton.setOnClickListener {
            getLocation()

        }
        binding.btnChooseLocation.setOnClickListener {
            sharedViewModel.saveLocation(myLoc!!)
            binding.btnChooseLocation.visibilityGone()
            binding.btnCancelTrip.visibilityGone()
            binding.imageButton.visibilityGone()
            sheet.state = BottomSheetBehavior.STATE_EXPANDED
            Constants.isBottomSheetOn = true

            // Check if it's a pickup or drop-off
            if (pickUpOrDropOf == 1) {
                if (pickUpMarker == null) {
                    Constants.pickUpLatLng?.let { latLng ->
                        pickUpMarker = addMarker(latLng)
                        binding.tvYourLocation.text=getAddressFromLatLng(latLng)
                        pickUpMarker?.title = getAddressFromLatLng(latLng)
                    }
                }
                pickUpMarker?.position = Constants.pickUpLatLng!!

                // Hide the image view for pickup
                binding.imgLocationMarker.visibilityGone()
            } else if (pickUpOrDropOf == 2) {
                if (dropOffMarker == null) {
                    Constants.dropOffLatLng?.let { latLng ->
                        binding.tvWhereGo.text=getAddressFromLatLng(latLng)
                        dropOffMarker = addMarker(latLng)
                        dropOffMarker?.title = getAddressFromLatLng(latLng)
                    }
                }
                    dropOffMarker?.position = Constants.dropOffLatLng!!


                // Hide the image view for drop-off
                binding.imgLocationMarker.visibilityGone()
            }
        }
    }
    private fun addMarker(latLng: LatLng): Marker {
        val bitmapDescriptor =
            BitmapDescriptorFactory.fromBitmap(MapUtils.getMarkerForSeclctionPosition(requireContext()))
        return mMap.addMarker(
            MarkerOptions().position(latLng).flat(true).icon(bitmapDescriptor)
        )!!
    }


    private fun dealWithSharedViewModel() {
        sharedViewModel.getLocationType().observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                LocationState.PICKUP_LOC.name -> {
                    pickUpOrDropOf = 1
                    binding.imgLocationMarker.visibilityVisible()
                    binding.btnCancelTrip.visibilityVisible()
                    binding.btnChooseLocation.visibilityVisible()
                    sheet.state = BottomSheetBehavior.STATE_COLLAPSED
                    Constants.isBottomSheetOn = false
                    setLocation(false)


                }
                LocationState.DROP_LOC.name -> {
                    pickUpOrDropOf = 2
                    binding.imgLocationMarker.visibilityVisible()
                    binding.btnCancelTrip.visibilityVisible()
                    binding.btnChooseLocation.visibilityVisible()
                    sheet.state = BottomSheetBehavior.STATE_COLLAPSED
                    Constants.isBottomSheetOn = false
                    setLocation(false)

                }
                LocationState.CANCEL.name -> {
                    clearMap()
                }
                LocationState.CONTINUE.name -> {
                    val builder = LatLngBounds.Builder()
                    builder.include(Constants.pickUpLatLng!!)
                    builder.include(Constants.dropOffLatLng!!)
                    val bounds = builder.build()
                    mMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 200))

                }
                else -> {
                }
            }
        })

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
    private fun showBottomSheet() {
        val inflater = myNavHostFragment?.navController?.navInflater
        val graph = inflater?.inflate(R.navigation.bottom_nav_graph)
        myNavHostFragment?.navController?.graph = graph!!
        sheet.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun initBottomSheets() {
        bottomSheetView = binding.findLocationBottomSheet.tripBottomSheet
        sheet = BottomSheetBehavior.from(bottomSheetView!!)
        sheet.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == 4) {
                    Constants.isBottomSheetOn = false
                } else if (newState == 2) {
                    Constants.isBottomSheetOn = true
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

        })
    }

    private fun handleBottomSheetSize() {

        myNavHostFragment?.navController?.addOnDestinationChangedListener { _, destination, arguments ->
            if (destination.id == R.id.findLocationFragment) {
                changeHeightOfSheet(requireContext(), 0.70)
                sheet.isDraggable = false

            } else if (destination.id == R.id.bottomSheetTripDetailsFragment || destination.id == R.id.bottomSheetRequestTripFragment) {
                changeHeightOfSheet(requireContext(), 0.40)
                sheet.isDraggable = true
            } else {
                changeHeightOfSheet(requireContext(), 0.40)
                sheet.isDraggable = true
            }

        }
    }

    private fun onBackPressHandle() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            val childFragment = myNavHostFragment?.childFragmentManager?.fragments

            if (childFragment?.size != 0 && Constants.isBottomSheetOn) {
                var fragment = childFragment?.get(0)

                if ((fragment is FindLocationFragment)
                    && sheet.state == BottomSheetBehavior.STATE_EXPANDED
                ) {
                    if (fragment is FindLocationFragment) {
                        sheet.state = BottomSheetBehavior.STATE_COLLAPSED
                        binding.locationCard.visibilityVisible()
                        binding.imageButton.visibilityVisible()
                    }
                }
            } else {
                requireActivity().finish()
            }
        }
    }

    private fun changeHeightOfSheet(context: Context, percent: Double) {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        binding.findLocationBottomSheet.navHostFragment11.layoutParams.height =
            (displayMetrics.heightPixels * percent).toInt()
    }
    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.
            checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), 2
            )

        } else {
            locationChecker()
        }
    }

    private fun setUpDarkTheme(map: GoogleMap) {
        mMap = map
        try {
            val success = map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style_dark)
            )
            if (!success) {
                Log.e("TAG", "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e("TAG", "Can't find style. Error: ", e)
        }
    }
    private fun clearMap(){
        sheet.state = BottomSheetBehavior.STATE_COLLAPSED
        binding.locationCard.visibilityVisible()
        setLocation(true)
        mMap.clear()
        moveCameraMap(myLoc!!)
        pickUpMarker = null
        dropOffMarker = null
        Constants.pickUpLatLng = myLoc
        Constants.dropOffLatLng = null
        pickUpOrDropOf = 0
        Constants.isBottomSheetOn = false
    }
    private fun setLocation(status:Boolean){
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
        }
        mMap.isMyLocationEnabled = status
    }
}