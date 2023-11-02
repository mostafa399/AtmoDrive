package com.mostafahelal.AtmoDrive.maps.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class SharedViewModel : ViewModel() {
    val locTypeLiveData = MutableLiveData<String>()
    val locationLiveData = MutableLiveData<LatLng>()

    fun setLocType(type: String) {
        locTypeLiveData.value = type
    }

    fun saveLocation(location: LatLng) {
        locationLiveData.value = location
    }

    fun getLocationType(): LiveData<String> = locTypeLiveData
    fun getLocation(): LiveData<LatLng> = locationLiveData

}
