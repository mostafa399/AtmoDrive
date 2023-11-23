package com.mostafahelal.AtmoDrive.auth.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mostafahelal.AtmoDrive.Utils.NetworkState
import com.mostafahelal.AtmoDrive.Utils.Constants
import com.mostafahelal.AtmoDrive.Utils.ISharedPreferencesManager
import com.mostafahelal.AtmoDrive.Utils.Resource
import com.mostafahelal.AtmoDrive.auth.domain.model.NewPassengerResponse
import com.mostafahelal.AtmoDrive.auth.domain.use_case.IAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterPassengerViewModel @Inject constructor(
    private val authUseCase: IAuthUseCase,
    private val iSharedPreferencesManager: ISharedPreferencesManager
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
                    if (response.data?.status==true) {
                        _RegisterState.value = NetworkState.getLoaded(response)
                        saveData(response)
//                        mySharedPreferences. saveUserAccessToken("${response.data.data.rememberToken}")
//                        mySharedPreferences.saveRegisterResponse(response.data.data)
                    } else if (response.isFailed()){
                        _RegisterState.value = NetworkState.getErrorMessage("API request failed Register request failed")
                    }
            }
        }
                } catch (e: Exception) {
                    _RegisterState.value = NetworkState.getErrorMessage(e)
                }
            }


    fun saveData(response: Resource<NewPassengerResponse>){
        iSharedPreferencesManager.saveString(Constants.AVATAR,response.data?.data?.avatar)
        iSharedPreferencesManager.saveString(Constants.EMAIL, response.data?.data?.email)
        iSharedPreferencesManager.saveString(Constants.FULLNAME,response.data?.data?.fullName)
        iSharedPreferencesManager.saveString(Constants.ISDARKMODE,response.data?.data?.isDarkMode.toString())
        iSharedPreferencesManager. saveString(Constants.LANG, response.data?.data?.lang)
        iSharedPreferencesManager.saveString(Constants.MOBILE, response.data?.data?.mobile)
        iSharedPreferencesManager.saveString(Constants.PASSENGERCODE, response.data?.data?.passengerCode)
        iSharedPreferencesManager.saveString(Constants.TOKEN, response.data?.data?.rememberToken)
        iSharedPreferencesManager.saveString(Constants.STATUS,response.data?.data?.status.toString())
        iSharedPreferencesManager.saveString(Constants.SUSPEND,response.data?.data?.suspend.toString())

    }

}