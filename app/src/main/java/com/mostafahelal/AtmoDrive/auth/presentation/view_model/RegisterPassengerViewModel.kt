package com.mostafahelal.AtmoDrive.auth.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.NetworkState
import com.mostafahelal.AtmoDrive.auth.data.data_source.local.MySharedPreferences
import com.mostafahelal.AtmoDrive.auth.domain.use_case.IAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterPassengerViewModel @Inject constructor(
    private val authUseCase: IAuthUseCase,
    private val mySharedPreferences: MySharedPreferences
):ViewModel() {

    private val _RegisterState: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val registerState: StateFlow<NetworkState?> = _RegisterState

    fun registerPassenger(full_name:String,
                          mobile:String,
                          avatar:String,
                          device_token:String,
                          device_id:String,
                          device_type:String,
                          email:String?){

        try {
        viewModelScope.launch {
           val response=authUseCase.registerPassenger(full_name, mobile, avatar, device_token, device_id, device_type,email)
            _RegisterState.value = NetworkState.LOADING
            if (response.isSuccessful()){
                    if (response.isSuccessful()) {
                        _RegisterState.value = NetworkState.getLoaded(response)
                        mySharedPreferences.saveRegisterResponse(response.data!!.data)
                    } else if (response.isFailed()){
                        _RegisterState.value = NetworkState.getErrorMessage("API request failed Register request failed")
                    }
            }
        }
                } catch (e: Exception) {
                    _RegisterState.value = NetworkState.getErrorMessage(e)
                }
            }




}