package com.mostafahelal.AtmoDrive.maps.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mostafahelal.AtmoDrive.Utils.NetworkState
import com.mostafahelal.AtmoDrive.maps.domain.use_case.ITripUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MakeTripViewModel @Inject constructor(private val iMakeTripUseCase: ITripUseCase) :ViewModel(){
    private val _makeTripResult: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val makeTripResult: StateFlow<NetworkState?> =_makeTripResult
    fun makeTrip(distanceText: String,
                 distanceValue: Long,
                 durationText: String,
                 durationValue: Long) {
        _makeTripResult.value = NetworkState.LOADING
        viewModelScope.launch {
            try {
                val result = iMakeTripUseCase.makeTrip(distanceText, distanceValue, durationText, durationValue)
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
}