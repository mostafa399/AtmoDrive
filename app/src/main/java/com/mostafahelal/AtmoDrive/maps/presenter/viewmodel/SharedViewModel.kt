package com.mostafahelal.AtmoDrive.maps.presenter.viewmodel

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.mostafahelal.AtmoDrive.maps.domain.model.MakeTripData
import java.io.IOException
import java.util.Locale

class SharedViewModel  : ViewModel() {
    val locTypeLiveData = MutableLiveData<String>()
    val locationLiveData = MutableLiveData<LatLng>()
    val makeTripData = MutableLiveData<MakeTripData>()
    val requestTrip = MutableLiveData<Boolean>()

    fun setLocType(type: String) {
        locTypeLiveData.value = type
    }

    fun saveLocation(location: LatLng) {
        locationLiveData.value = location
    }

    fun getLocationType(): LiveData<String> = locTypeLiveData
    fun getLocation(): LiveData<LatLng> = locationLiveData
    fun setMakeTripData(trip:MakeTripData){
        makeTripData.value = trip
    }

    fun setRequestTrip(request:Boolean){
        requestTrip.value = request
    }
    fun getMakeTripData():LiveData<MakeTripData> =makeTripData

    fun getRequestTrip():LiveData<Boolean> =requestTrip

    fun getAddressFromLatLng(latLng: LatLng,context: Context): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        var address = ""
        try {
            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (addresses != null && addresses.isNotEmpty()) {
                val firstAddress: Address = addresses[0]
                val stringBuilder = StringBuilder()

                for (i in 0..firstAddress.maxAddressLineIndex) {
                    stringBuilder.append(firstAddress.getAddressLine(i))
                    if (i < firstAddress.maxAddressLineIndex) {
                        stringBuilder.append(", ")
                    }
                }

                address = stringBuilder.toString()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return address
    }

}
