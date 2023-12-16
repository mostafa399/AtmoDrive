package com.mostafahelal.AtmoDrive.maps.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mostafahelal.AtmoDrive.Utils.Constants
import com.mostafahelal.AtmoDrive.Utils.ISharedPreferencesManager
import com.mostafahelal.AtmoDrive.Utils.LocationHelper
import com.mostafahelal.AtmoDrive.Utils.NetworkState
import com.mostafahelal.AtmoDrive.Utils.Resource
import com.mostafahelal.AtmoDrive.maps.domain.model.CaptainDetails
import com.mostafahelal.AtmoDrive.maps.domain.use_case.ITripUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class TripViewModel @Inject constructor(
    private val iTripUseCase: ITripUseCase,
    private val iSharedPreferencesManager: ISharedPreferencesManager
): ViewModel() {
    private val _makeTrip: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val makeTrip: StateFlow<NetworkState?> =_makeTrip

    private val _confirmTrip: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val confirmTrip: StateFlow<NetworkState?> =_confirmTrip

    private val _cancelBeforeCaptain: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val cancelBeforeCaptain: StateFlow<NetworkState?> =_cancelBeforeCaptain

    private val _getCaptainDetails: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val getCaptainDetails: StateFlow<NetworkState?> =_getCaptainDetails

    private val _tripDetails: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val tripDetails: StateFlow<NetworkState?> =_tripDetails

    private val _onTrip: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val onTrip: StateFlow<NetworkState?> =_onTrip

    private val _cancelTrip: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val cancelTrip: StateFlow<NetworkState?> =_cancelTrip




    fun makeTrip() {
        _makeTrip.value = NetworkState.LOADING
        viewModelScope.launch {
            val distance =
                LocationHelper.getEstimatedDistance(Constants.pickUpLatLng!!, Constants.dropOffLatLng!!)
            val duration =
                LocationHelper.getEstimatedTime(Constants.pickUpLatLng!!, Constants.dropOffLatLng!!)

            try {
                val result = iTripUseCase.makeTrip("$distance KM", distance * 1000, "$duration min", duration)
                if (result.isSuccessful()){
                    _makeTrip.value = NetworkState.getLoaded(result)
                }else{
                    _makeTrip.value = NetworkState.getErrorMessage(result.message.toString())
                }
            }catch (ex:Exception){
                ex.printStackTrace()
                _makeTrip.value = NetworkState.getErrorMessage(ex)
            }
        }
    }
    fun confirmTrip( vehicle_class_id: String,
                     pickup_lat: String,
                     pickup_lng: String,
                     dropoff_lat: String,
                     dropoff_lng: String,
                     estimate_cost: String,
                     estimate_time: String,
                     estimate_distance: String,
                     pickup_location_name: String,
                     dropoff_location_name: String
    ) {
        _confirmTrip.value = NetworkState.LOADING
        viewModelScope.launch {
            try {
                val result = iTripUseCase.confirmTrip(
                    vehicle_class_id,
                    pickup_lat,
                    pickup_lng,
                    dropoff_lat,
                    dropoff_lng,
                    estimate_cost,
                    estimate_time,
                    estimate_distance,
                    pickup_location_name,
                    dropoff_location_name)
                if (result.isSuccessful()){
                    _confirmTrip.value = NetworkState.getLoaded(result)
                }else{
                    _confirmTrip.value = NetworkState.getErrorMessage(result.message.toString())
                }
            }catch (ex:Exception){
                ex.printStackTrace()
                _confirmTrip.value = NetworkState.getErrorMessage(ex)
            }
        }
    }
    fun cancelBeforeCaptain(tripid:Int) {
        _cancelBeforeCaptain.value = NetworkState.LOADING
        viewModelScope.launch {
            try {
                val result = iTripUseCase.cancelTripBeforeCaptainAccepts(tripid)
                if (result.isSuccessful()){
                    _cancelBeforeCaptain.value = NetworkState.getLoaded(result)
                }else{
                    _cancelBeforeCaptain.value = NetworkState.getErrorMessage(result.message.toString())
                }
            }catch (ex:Exception){
                ex.printStackTrace()
                _cancelBeforeCaptain.value = NetworkState.getErrorMessage(ex)
            }
        }
    }
    fun getCaptainDetails(tripId:Int) {
        _getCaptainDetails.value = NetworkState.LOADING
        viewModelScope.launch {
            try {

                val result = iTripUseCase.getCaptainDetails(tripId)
                if (result.data?.status!!){
                    _getCaptainDetails.value = NetworkState.getLoaded(result)
                    saveData(result)
                }else{
                    _getCaptainDetails.value = NetworkState.getErrorMessage(result.message.toString())
                }
            }catch (ex:Exception){
                ex.printStackTrace()
                _getCaptainDetails.value = NetworkState.getErrorMessage(ex)
            }
        }
    }
    fun cancelTrip(tripId:Int){
        _cancelTrip.value = NetworkState.LOADING
        viewModelScope.launch {
            try {
                val result = iTripUseCase.cancelTrip(tripId)
                if (result.isSuccessful()){
                    _cancelTrip.value = NetworkState.getLoaded(result)
                }else{
                    _cancelTrip.value = NetworkState.getErrorMessage(result.message.toString())
                }
            }catch (ex:Exception){
                ex.printStackTrace()
                _cancelTrip.value = NetworkState.getErrorMessage(ex)
            }
        }
    }
    fun getTripDetails(tripId:Int){
        _tripDetails.value = NetworkState.LOADING
        viewModelScope.launch {
            try {
                val result = iTripUseCase.getTripDetails(tripId)
                if (result.isSuccessful()){
                    _tripDetails.value = NetworkState.getLoaded(result)
                }else{
                    _tripDetails.value = NetworkState.getErrorMessage(result.message.toString())
                }
            }catch (ex:Exception){
                ex.printStackTrace()
                _tripDetails.value = NetworkState.getErrorMessage(ex)
            }
        }
    }
    fun onTrip() {
        _onTrip.value = NetworkState.LOADING
        viewModelScope.launch {
            try {
                val result = iTripUseCase.onTrip()
                if (result.isSuccessful()){
                    _onTrip.value = NetworkState.getLoaded(result)
                }else{
                    _onTrip.value = NetworkState.getErrorMessage(result.message.toString())
                }
            }catch (ex:Exception){
                ex.printStackTrace()
                _onTrip.value = NetworkState.getErrorMessage(ex)
            }
        }
    }
    fun saveData(response: Resource<CaptainDetails>){
        iSharedPreferencesManager.saveString(Constants.CaptainName,response.data?.cap?.fullName)
        iSharedPreferencesManager.saveString(Constants.CaptainCost, response.data?.cap?.estimateCost.toString())
        iSharedPreferencesManager.saveString(Constants.CaptainMobile,response.data?.cap?.mobile)
        iSharedPreferencesManager.saveString(Constants.CAptainAvatar,response.data?.cap?.avatar)
    }

}