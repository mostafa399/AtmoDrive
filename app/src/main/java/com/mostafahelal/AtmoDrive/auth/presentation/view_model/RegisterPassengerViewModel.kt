package com.mostafahelal.AtmoDrive.auth.presentation.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.NetworkState
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.Resource
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.SharedPreference
import com.mostafahelal.AtmoDrive.auth.data.data_source.local.ISharedPrefrenceManager
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.RegisterPassengerRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.RegisterPassengerResponse
import com.mostafahelal.AtmoDrive.auth.domain.model.NewPassengerRequest
import com.mostafahelal.AtmoDrive.auth.domain.use_case.AuthUseCase
import com.mostafahelal.AtmoDrive.auth.domain.use_case.IAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterPassengerViewModel @Inject constructor(
    private val authUseCase: IAuthUseCase,
    private val sharedPreference: ISharedPrefrenceManager
):ViewModel() {

    private val _RegisterState: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val registerState: StateFlow<NetworkState?> = _RegisterState

    fun registerPassenger(passenger: RegisterPassengerRequest){
        try {
        viewModelScope.launch {
           val response=authUseCase.registerPassenger(passenger)
            _RegisterState.value = NetworkState.LOADING
            if (response.isSuccessful()){
                    if (response.isSuccessful()) {
                        _RegisterState.value = NetworkState.getLoaded(response.data)
                        sharedPreference.saveRegisterResponse(response.data)
                    } else if (response.isFailed()){
                        _RegisterState.value = NetworkState.getErrorMessage("API request failed Register request failed")
                       // _RegisterState.value=NetworkState.getLoaded(response.data)
                    }
            }
        }
                } catch (e: Exception) {
                    _RegisterState.value = NetworkState.getErrorMessage(e)
                }
            }

    fun getCachedRegisterResponse(): RegisterPassengerResponse? {
        return sharedPreference.getRegisterResponse()
    }


}