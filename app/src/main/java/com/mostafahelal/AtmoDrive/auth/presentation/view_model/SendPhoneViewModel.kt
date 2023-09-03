package com.mostafahelal.AtmoDrive.auth.presentation.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.NetworkState
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.Resource
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.SharedPreference
import com.mostafahelal.AtmoDrive.auth.data.data_source.local.ISharedPrefrenceManager
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.SendCodeResponse
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
class SendPhoneViewModel @Inject constructor(
    private val authUseCase: IAuthUseCase,
    private val sharedPreference: ISharedPrefrenceManager
):ViewModel() {
    private val _SendCodeResult:MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val sendCodeResult:StateFlow<NetworkState?> = _SendCodeResult
    fun sendMobilePhone(phone: String) {
        viewModelScope.launch {
            _SendCodeResult.value = NetworkState.LOADING
            try {
                val response = authUseCase.sendPhone(phone)
                if (response.isSuccessful()) {
                    _SendCodeResult.value = NetworkState.getLoaded(response.data)
                } else if (response.isFailed()){
                    _SendCodeResult.value = NetworkState.getErrorMessage("API request failed Send code request failed")
                    _SendCodeResult.value=NetworkState.getLoaded(response.data)
                }
            } catch (e: Exception) {
                _SendCodeResult.value = NetworkState.getErrorMessage(e)
            }
        }
    }
}