package com.mostafahelal.AtmoDrive.maps.presenter

import android.app.Application
import android.location.Geocoder
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.mostafahelal.AtmoDrive.maps.domain.model.MakeTripData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.IOException
import java.util.Locale

/*class SharedViewModel :ViewModel() {
    private var _location = MutableLiveData<String>()
    val location : LiveData<String> =_location

    val locType = MutableLiveData<String>()
    private var _click=MutableLiveData<Int>()
    val loc:LiveData<Int> = _click


    fun saveClick(click:Int){
        _click.value=click
    }

    fun saveLocation(loc: String) {
        _location.value = loc
    }
    fun setLocType(type: String){
        locType.value = type
    }

}

 */
class SharedViewModel : ViewModel() {
    private val locTypeLiveData = MutableLiveData<String>()
    private val locationLiveData = MutableLiveData<LatLng>()

    fun setLocType(type: String) {
        locTypeLiveData.value = type
    }

    fun saveLocation(location: LatLng) {
        locationLiveData.value = location
    }

    fun getLocationType(): LiveData<String> = locTypeLiveData
    fun getLocation(): LiveData<LatLng> = locationLiveData
}
