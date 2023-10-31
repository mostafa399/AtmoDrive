package com.mostafahelal.AtmoDrive.auth.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mostafahelal.AtmoDrive.auth.data.data_source.local.MySharedPreferences
import com.mostafahelal.AtmoDrive.Utils.NetworkState
import com.mostafahelal.AtmoDrive.auth.domain.use_case.IAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CheckCodeViewModel @Inject constructor(
    private val authUseCase: IAuthUseCase,
    private val mySharedPreferences: MySharedPreferences
):ViewModel() {
    private val _NavigateToRegister: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val navigateToRegister: StateFlow<NetworkState?> = _NavigateToRegister
    private val _NavigateToMain: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val navigateToMain: StateFlow<NetworkState?> = _NavigateToMain
    fun checkCode(deviceToken:String, mobile:String, verificationCode: String){
        viewModelScope.launch {
            try {
                val response=authUseCase.checkCode(deviceToken,mobile,verificationCode)
                if (response.isSuccessful()){
                    if (response.data?.is_new==true && response.data.status){
                        _NavigateToRegister.value = NetworkState.getLoaded(response)
                    }
                    else if (response.data?.is_new==false&&response.data.status){
                        _NavigateToMain.value = NetworkState.getLoaded(response)
                        mySharedPreferences. saveUserAccessToken("${response.data.data.rememberToken}")
                        mySharedPreferences.saveRegisterResponse(response.data.data)
                    }
                }else if (response.isFailed()){
                    _NavigateToMain.value = NetworkState.getErrorMessage("Check code request failed3")
                    _NavigateToRegister.value= NetworkState.getErrorMessage("Check code request failed3")

                }

            }catch (e:Exception){
                _NavigateToRegister.value = NetworkState.getErrorMessage(e)
                _NavigateToMain.value = NetworkState.getErrorMessage(e)

            }
        }
    }

}