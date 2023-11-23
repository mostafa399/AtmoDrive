package com.mostafahelal.AtmoDrive.maps.presenter.view

import android.Manifest
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Handler
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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
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
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.ktx.database
import com.mostafahelal.AtmoDrive.MainActivity
import com.mostafahelal.AtmoDrive.R
import com.mostafahelal.AtmoDrive.Utils.AnimationUtils
import com.mostafahelal.AtmoDrive.Utils.Constants
import com.mostafahelal.AtmoDrive.Utils.ISharedPreferencesManager
import com.mostafahelal.AtmoDrive.Utils.LocationHelper
import com.mostafahelal.AtmoDrive.Utils.LocationState
import com.mostafahelal.AtmoDrive.Utils.MapUtils
import com.mostafahelal.AtmoDrive.Utils.NetworkState
import com.mostafahelal.AtmoDrive.Utils.Resource
import com.mostafahelal.AtmoDrive.Utils.TripObject
import com.mostafahelal.AtmoDrive.Utils.showToast
import com.mostafahelal.AtmoDrive.Utils.visibilityGone
import com.mostafahelal.AtmoDrive.Utils.visibilityVisible
import com.mostafahelal.AtmoDrive.databinding.ActivityMapsBinding
import com.mostafahelal.AtmoDrive.databinding.FragmentMaps2Binding
import com.mostafahelal.AtmoDrive.maps.domain.model.TripDetails
import com.mostafahelal.AtmoDrive.maps.presenter.viewmodel.SharedViewModel
import com.mostafahelal.AtmoDrive.maps.presenter.viewmodel.TripViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MapsFragment : Fragment(),OnMapReadyCallback {
    @Inject
    lateinit var shared:ISharedPreferencesManager
    private lateinit var binding: FragmentMaps2Binding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var mMap: GoogleMap
    private var mapFragment: SupportMapFragment? = null
    private var mLocationRequest: LocationRequest? = null
    private var mLocationCallback: LocationCallback? = null
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private var bottomSheetView: ConstraintLayout? = null
    private var sheet=  BottomSheetBehavior<ConstraintLayout>()
    private var myNavHostFragment: NavHostFragment? = null
    private var pickUpMarker: Marker? = null
    private var dropOffMarker: Marker? = null
    private var address=""
    private var myLoc: LatLng? = null
    private var pickUpOrDropOf = 0
    private var valueEventListener : ValueEventListener?= null
    private lateinit var database: DatabaseReference
    private var status = ""
    private var mBackPressed: Long = 0
    private var movingCabMarker : Marker?= null
    private var previousLatLng: LatLng? = null
    private var currentLatLng: LatLng? = null
    private var valueAnimator: ValueAnimator? = null
    private val viewModel: TripViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMaps2Binding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMaps2Binding.bind(view)
        myNavHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment11) as NavHostFragment

        viewModel.onTrip()
//        mMap.isMyLocationEnabled = true
        init()
        initBottomSheets()
        handleBottomSheetSize()
        onBackPressHandle()
        dealWithSharedViewModel()
        onClicks()
        observeOnRequestTrip()
        observer()

    }

    private fun init() {
        //initMap
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment!!.getMapAsync(this)

        database = Firebase.database.reference
        if (mFusedLocationProviderClient == null) {
            mFusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(requireActivity())
        }
        if (mLocationRequest == null) {
            mLocationRequest =  LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 15 * 60 * 1000)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(2000)
                .setMaxUpdateDelayMillis(2000)
                .setMinUpdateDistanceMeters(5f).build()
        }
    }
    @SuppressLint("MissingPermission")
    private fun getLocation() {
        val currentLocationTask: Task<Location> = mFusedLocationProviderClient!!.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY, null
        )
        currentLocationTask.addOnCompleteListener { task: Task<Location> ->
            if (task.isSuccessful && task.result != null) {
                val result: Location = task.result
                myLoc = LatLng(result.latitude, result.longitude)
                mMap.isMyLocationEnabled=true
                moveCameraMap(LatLng(result.latitude, result.longitude))
                address=getAddressFromLatLng(myLoc!!)
                binding.tvYourLocation.text=address

            }
        }

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
        mMap.isMyLocationEnabled=true

        if (resources.getString(R.string.mode) == "Night") {
            setUpDarkTheme(mMap)
        }
        mMap.setOnCameraIdleListener {
//            mMap.isMyLocationEnabled=false
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
        // logout
        binding.imgCategory.setOnClickListener {
            shared.clearString(Constants.TOKEN)
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }


        // CardView
        binding.locationCard.setOnClickListener {
            Constants.pickUpLatLng=myLoc
            binding.imageButton.visibilityGone()
            binding.locationCard.visibilityGone()
            showBottomSheet(R.navigation.bottom_nav_graph)
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
            clearMap()
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
        binding.tvCancelFindCaptain.setOnClickListener {
            binding.layoutFindLocation.visibilityGone()

            // cancel trip
            viewModel.cancelBeforeCaptain()

            clearMap()

        }
    }
    private fun addMarker(latLng: LatLng): Marker {
        val bitmapDescriptor =
            BitmapDescriptorFactory.fromBitmap(MapUtils.getMarkerForSeclctionPosition(requireContext()))
        return mMap.addMarker(
            MarkerOptions().position(latLng).flat(true).icon(bitmapDescriptor)
        )!!
    }


    @SuppressLint("MissingPermission")
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
                    mMap.isMyLocationEnabled=false


                }
                LocationState.DROP_LOC.name -> {
                    pickUpOrDropOf = 2
                    binding.imgLocationMarker.visibilityVisible()
                    binding.btnCancelTrip.visibilityVisible()
                    binding.btnChooseLocation.visibilityVisible()
                    sheet.state = BottomSheetBehavior.STATE_COLLAPSED
                    Constants.isBottomSheetOn = false
                    mMap.isMyLocationEnabled=false

                }
                LocationState.CANCEL.name -> {
                    clearMap()
                }
                LocationState.CONTINUE.name -> {
                  showPath(Constants.pickUpLatLng!!,Constants.dropOffLatLng!!)
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
    private fun showBottomSheet(nav:Int) {
        val inflater = myNavHostFragment?.navController?.navInflater
        val graph = inflater?.inflate(nav)
        myNavHostFragment?.navController?.graph = graph!!
        sheet.state = BottomSheetBehavior.STATE_EXPANDED
        Constants.isBottomSheetOn = true

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
                changeHeightOfSheet( 0.60)
                sheet.isDraggable = false

            } else if (destination.id == R.id.bottomSheetRequestTripFragment) {
                changeHeightOfSheet( 0.45)
                sheet.isDraggable = false

            } else if (destination.id == R.id.bottomSheetTripDetailsFragment2) {
                changeHeightOfSheet(0.40)
                sheet.isDraggable = false
            }
            else {
                changeHeightOfSheet( 0.40)
                sheet.isDraggable = false
            }

        }
    }

    private fun onBackPressHandle() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            val childFragment = myNavHostFragment?.childFragmentManager?.fragments

            if (childFragment?.size != 0 && Constants.isBottomSheetOn) {
                var fragment = childFragment?.get(0)

                if ((fragment is FindLocationFragment ||fragment is BottomSheetTripDetailsFragment)
                    && sheet.state == BottomSheetBehavior.STATE_EXPANDED
                ) {
                    if (fragment is FindLocationFragment){
                        clearMap()

                    }

//                        sheet.state = BottomSheetBehavior.STATE_COLLAPSED
//                        binding.locationCard.visibilityVisible()
//                        binding.imageButton.visibilityVisible()
                }
            } else {
                requireActivity().finish()
            }
        }
    }

    private fun changeHeightOfSheet(percent: Double) {
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        binding.findLocationBottomSheet.navHostFragment11.layoutParams.height =
            (displayMetrics.heightPixels * percent).toInt()
       // sheet.peekHeight = (displayMetrics.heightPixels * percent).toInt()

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
    @SuppressLint("MissingPermission")
    private fun clearMap(){
        sheet.state = BottomSheetBehavior.STATE_COLLAPSED
        binding.locationCard.visibilityVisible()
        binding.imageButton.visibilityVisible()
        mMap.isMyLocationEnabled=true
        mMap.clear()
        moveCameraMap(myLoc!!)
        pickUpMarker = null
        dropOffMarker = null
        movingCabMarker = null
        Constants.pickUpLatLng = myLoc
        Constants.dropOffLatLng = null
        pickUpOrDropOf = 0
        status = ""
        binding.tvWhereGo.text=""
        binding.tvYourLocation.text=sharedViewModel.getAddressFromLatLng(myLoc!!,requireContext())
        Constants.isBottomSheetOn = false
    }
    private fun listenerOnTrip(){
        valueEventListener =  object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val trip = snapshot.getValue(TripObject::class.java)

                val tripStatus = trip?.status

                if(trip?.status != null&&tripStatus != status){
                    binding.layoutFindLocation.visibilityGone()
                    showBottomSheet(R.navigation.trip_nav_graph)
                    status = tripStatus!!
                    val captainLatLng = LatLng(trip.lat.toDouble(),trip.lng.toDouble())
                    when (status){
                        "accepted" -> {
                            showPath(captainLatLng,Constants.pickUpLatLng!!)
                        }
                        "on_the_way" -> {
                            showPath(captainLatLng,Constants.pickUpLatLng!!)
                        }
                        "arrived" -> {
                            showPath(captainLatLng,Constants.dropOffLatLng!!)
                            pickUpMarker?.remove()

                        }
                        "start_trip" -> {
                            pickUpMarker?.remove()
                            showPath(captainLatLng,Constants.dropOffLatLng!!)                        }
                        "pay" -> {
                            dropOffMarker?.remove()

                        }
                    }
                }

                if (trip != null){
                    updateCarLocation(LatLng(trip.lat.toDouble(),trip.lng.toDouble()))
                }else {

                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        }

        database.child("trips").child(Constants.tripId.toString())
            .addValueEventListener(valueEventListener!!)
    }
    private fun showPath(pickUp:LatLng,dropOff:LatLng){
        val builder = LatLngBounds.Builder()
        builder.include(pickUp)
        builder.include(dropOff)
        val bounds = builder.build()
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150))

        //draw path

    }
    private fun updateCarLocation(latLng: LatLng) {
        val a = System.currentTimeMillis() - mBackPressed
        if (a < 2600) {
            return
        }
        mBackPressed = System.currentTimeMillis()
        if (movingCabMarker == null) {
            movingCabMarker = addCarMarkerAndGet(latLng)
        }
        if (previousLatLng == null) {
            currentLatLng = latLng
            previousLatLng = currentLatLng
            movingCabMarker?.position = currentLatLng!!
            movingCabMarker?.setAnchor(0.5f, 0.5f)
            animateCameraInTrip(currentLatLng!!, previousLatLng!!)
        } else {
            // animateCameraInTrip(currentLatLng!!, previousLatLng!!)
            previousLatLng = currentLatLng

            animateCameraInTrip(latLng, previousLatLng!!)
            valueAnimator = AnimationUtils.carAnimator()

            valueAnimator?.addUpdateListener { va ->
                currentLatLng = latLng
                if (currentLatLng != null && previousLatLng != null) {

                    val multiplier = va.animatedFraction
                    val nextLocation = LatLng(
                        multiplier * currentLatLng!!.latitude + (1 - multiplier) * previousLatLng!!.latitude,
                        multiplier * currentLatLng!!.longitude + (1 - multiplier) * previousLatLng!!.longitude
                    )
                    movingCabMarker?.position = nextLocation
                    val rotation = MapUtils.getRotation(previousLatLng!!, nextLocation)
                    if (!rotation.isNaN()) {
                        movingCabMarker?.rotation = rotation
                    }
                    movingCabMarker?.setAnchor(0.5f, 0.5f)
                }
            }
            valueAnimator?.start()
        }

    }

    private fun animateCameraInTrip(latLng: LatLng, previous: LatLng) {

        val cameraPosition = CameraPosition.Builder()
            .bearing(LocationHelper.getBearing(previous, latLng))
            .target(latLng).zoom(16f).build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

    }
    private fun addCarMarkerAndGet(latLng: LatLng): Marker {
        val bitmapDescriptor =
            BitmapDescriptorFactory.fromBitmap(MapUtils.getCarBitmap(requireContext()))
        return mMap.addMarker(
            MarkerOptions().position(latLng).flat(true).icon(bitmapDescriptor)
        )!!
    }


    private fun observeOnRequestTrip(){
        sharedViewModel.getRequestTrip().observe(viewLifecycleOwner, Observer {

            if (it){
                findingCaptainWithTimeout()
                listenerOnTrip()
            }else if(!it){
                showToast("tripCanceled")
                clearMap()
            }

        })
    }
    private fun findingCaptain(){
        sheet.state = BottomSheetBehavior.STATE_COLLAPSED
        Constants.isBottomSheetOn = false
        binding.layoutFindLocation.visibilityVisible()
        binding.locationCard.visibilityGone()
        binding.imageButton.visibilityGone()
    }
    private fun findingCaptainWithTimeout() {
        // Set a timeout of 50 seconds
        val timeoutMillis = 50000L

        // Initialize a handler
        val handler = Handler()

        // Post a delayed runnable to check if findingCaptain has completed within the timeout
        handler.postDelayed({
            if (sheet.state != BottomSheetBehavior.STATE_COLLAPSED) {
                // If findingCaptain has not completed, show a toast
                showToast("No captains found")
            }
        }, timeoutMillis)

        // Call your original findingCaptain function
        findingCaptain()

        // Remove the callbacks to prevent them from running if findingCaptain completes within the timeout
        handler.removeCallbacksAndMessages(null)
    }

    private fun observer(){
        lifecycleScope.launch {
            viewModel.onTripResult.collect{ networkState ->
                when(networkState?.status){
                    NetworkState.Status.SUCCESS ->{
                        val data = networkState.data as Resource<TripDetails>
                        Constants.tripId = data.data?.tripData?.tripId!!
                        if(data.data.tripData.tripStatus!! == "pending"){
                            findingCaptainWithTimeout()
                        }else{
                            setUpTrip(data.data)
                            listenerOnTrip()
                        }
                    }
                    NetworkState.Status.FAILED ->{
                        showToast(networkState.msg.toString())
                    }
                    NetworkState.Status.RUNNING ->{
                    }
                    else -> {}
                }
            }
        }
        lifecycleScope.launch {
            viewModel.cancelBeforeCaptainResult.collect{ networkState ->
                when(networkState?.status){
                    NetworkState.Status.SUCCESS ->{
//                        val data = networkState.data as Resource<CancelBeforeCaptainAccept>
                        showToast("Trip Canceled Before Captain Accept ...!")
                    }
                    NetworkState.Status.FAILED ->{
                        showToast(networkState.msg.toString())
                    }
                    NetworkState.Status.RUNNING ->{
                    }
                    else -> {}
                }
            }
        }
        }
    @SuppressLint("MissingPermission")
    private fun setUpTrip(tripData:TripDetails){
        Constants.tripId = tripData.tripData?.tripId!!
        binding.locationCard.visibilityGone()
        showBottomSheet(R.navigation.trip_nav_graph)
        val pickUpLatLng = LatLng(tripData.tripData.pickupLat.toDouble(),tripData.tripData.pickupLng.toDouble())
        val dropOffLatLng = LatLng(tripData.tripData.dropoffLat.toDouble(),tripData.tripData.dropoffLng.toDouble())
        Constants.pickUpLatLng = pickUpLatLng
        Constants.dropOffLatLng = dropOffLatLng
        if(pickUpMarker == null){
            pickUpMarker = addMarker(pickUpLatLng)
        }
        pickUpMarker?.position = pickUpLatLng
        if (dropOffMarker == null){
            dropOffMarker = addMarker(dropOffLatLng)
        }
        dropOffMarker?.position = dropOffLatLng
        showPath(pickUpLatLng,dropOffLatLng)
        mMap.isMyLocationEnabled=false

    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (Constants.tripId != 0)
            database.child("trips").child(Constants.tripId.toString())
                .removeEventListener(valueEventListener!!)
    }
    }
