package com.mostafahelal.AtmoDrive.maps.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mostafahelal.AtmoDrive.Utils.Constants
import com.mostafahelal.AtmoDrive.Utils.ISharedPreferencesManager
import com.mostafahelal.AtmoDrive.Utils.LocationHelper
import com.mostafahelal.AtmoDrive.Utils.NetworkState
import com.mostafahelal.AtmoDrive.Utils.Resource
import com.mostafahelal.AtmoDrive.maps.domain.model.ConfirmTrip
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
    private val _makeTripResult: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val makeTripResult: StateFlow<NetworkState?> =_makeTripResult

    private val _confirmTrip: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val confirTrip: StateFlow<NetworkState?> =_confirmTrip

    private val _cancelBeforeCaptainResult: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val cancelBeforeCaptainResult: StateFlow<NetworkState?> =_cancelBeforeCaptainResult

    private val _getCaptainDetailsResult: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val getCaptainDetailsResult: StateFlow<NetworkState?> =_getCaptainDetailsResult

    private val _onTripResult: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val onTripResult: StateFlow<NetworkState?> =_onTripResult

    private val _cancelTripResult: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val cancelTripResult: StateFlow<NetworkState?> =_cancelTripResult

    private val _tripDetailsResult: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val tripDetailsResult: StateFlow<NetworkState?> =_tripDetailsResult
    val tripId=Constants.tripId



    fun makeTrip() {
        _makeTripResult.value = NetworkState.LOADING
        viewModelScope.launch {
            val distance =
                LocationHelper.getEstimatedDistance(Constants.pickUpLatLng!!, Constants.dropOffLatLng!!)
            val duration =
                LocationHelper.getEstimatedTime(Constants.pickUpLatLng!!, Constants.dropOffLatLng!!)

            try {
                val result = iTripUseCase.makeTrip("$distance KM", distance * 1000, "$duration min", duration)
                if (result.isSuccessful()){
                    _makeTripResult.value = NetworkState.getLoaded(result)
                }else{
                    _makeTripResult.value = NetworkState.getErrorMessage(result.message.toString())
                }
            }catch (ex:Exception){
                ex.printStackTrace()
                _makeTripResult.value = NetworkState.getErrorMessage(ex)
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
    fun saveData(response: Resource<ConfirmTrip>){
        iSharedPreferencesManager.saveString(Constants.TRIPID,response.data?.tripId.toString())

    }
    fun cancelBeforeCaptain() {
        _cancelBeforeCaptainResult.value = NetworkState.LOADING
        viewModelScope.launch {
            try {
                val result = iTripUseCase.cancelTripBeforeCaptainAccepts(tripId)
                if (result.isSuccessful()){
                    _cancelBeforeCaptainResult.value = NetworkState.getLoaded(result)
                }else{
                    _cancelBeforeCaptainResult.value = NetworkState.getErrorMessage(result.message.toString())
                }
            }catch (ex:Exception){
                ex.printStackTrace()
                _cancelBeforeCaptainResult.value = NetworkState.getErrorMessage(ex)
            }
        }
    }
    fun getCaptainDetails() {
        _getCaptainDetailsResult.value = NetworkState.LOADING
        viewModelScope.launch {
            try {

                val result = iTripUseCase.getCaptainDetails(tripId)
                if (result.isSuccessful()){
                    _getCaptainDetailsResult.value = NetworkState.getLoaded(result)
                }else{
                    _getCaptainDetailsResult.value = NetworkState.getErrorMessage(result.message.toString())
                }
            }catch (ex:Exception){
                ex.printStackTrace()
                _getCaptainDetailsResult.value = NetworkState.getErrorMessage(ex)
            }
        }
    }
    fun cancelTrip() {
        _cancelTripResult.value = NetworkState.LOADING
        viewModelScope.launch {
            try {
                val result = iTripUseCase.cancelTrip(tripId)
                if (result.isSuccessful()){
                    _cancelTripResult.value = NetworkState.getLoaded(result)
                }else{
                    _cancelTripResult.value = NetworkState.getErrorMessage(result.message.toString())
                }
            }catch (ex:Exception){
                ex.printStackTrace()
                _cancelTripResult.value = NetworkState.getErrorMessage(ex)
            }
        }
    }
    fun getTripDetails() {
        _tripDetailsResult.value = NetworkState.LOADING
        viewModelScope.launch {
            try {
                val result = iTripUseCase.getTripDetails(tripId)
                if (result.isSuccessful()){
                    _tripDetailsResult.value = NetworkState.getLoaded(result)
                }else{
                    _tripDetailsResult.value = NetworkState.getErrorMessage(result.message.toString())
                }
            }catch (ex:Exception){
                ex.printStackTrace()
                _tripDetailsResult.value = NetworkState.getErrorMessage(ex)
            }
        }
    }
    fun onTrip() {
        _onTripResult.value = NetworkState.LOADING
        viewModelScope.launch {
            try {
                val result = iTripUseCase.onTrip()
                if (result.isSuccessful()){
                    _onTripResult.value = NetworkState.getLoaded(result)
                }else{
                    _onTripResult.value = NetworkState.getErrorMessage(result.message.toString())
                }
            }catch (ex:Exception){
                ex.printStackTrace()
                _onTripResult.value = NetworkState.getErrorMessage(ex)
            }
        }
    }

}