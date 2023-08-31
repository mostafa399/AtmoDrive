package com.mostafahelal.AtmoDrive.auth.presentation.view_model

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.NetworkState
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.Resource
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.SharedPreference
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.CheckCodeRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.SendCodeRequest
import com.mostafahelal.AtmoDrive.auth.domain.use_case.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SendPhoneViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val sharedPreference: SharedPreference
):ViewModel() {
    private val _SendCodeResult:MutableStateFlow<NetworkState?> = MutableStateFlow(null)
     val sendCodeResult:StateFlow<NetworkState?> = _SendCodeResult
    fun sendMobilePhone(phone: String){
        authUseCase.sendPhone(phone).onEach {result->
            _SendCodeResult.value=NetworkState.LOADING
            when (result) {
                is Resource.Error -> {
                    Log.i("LoginViewModel", "Error ${result.message}")

                }
                is Resource.Success -> {
                    if (result.data?.status==1){
                        _SendCodeResult.value=NetworkState.getLoaded(result)
                    }else{
                        _SendCodeResult.value=NetworkState.getErrorMessage(result.data?.message.toString())
                    }
                    Log.i("LoginViewModel", "Success ${result.data?.message}")
                }
            }
        }.launchIn(viewModelScope)
    }


}