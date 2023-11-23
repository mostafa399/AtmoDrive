package com.mostafahelal.AtmoDrive.auth.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mostafahelal.AtmoDrive.Utils.Constants
import com.mostafahelal.AtmoDrive.Utils.ISharedPreferencesManager
import com.mostafahelal.AtmoDrive.Utils.NetworkState
import com.mostafahelal.AtmoDrive.Utils.Resource
import com.mostafahelal.AtmoDrive.auth.domain.model.CodeResponse
import com.mostafahelal.AtmoDrive.auth.domain.use_case.IAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CheckCodeViewModel @Inject constructor(
    private val authUseCase: IAuthUseCase,
    private val iSharedPreferencesManager: ISharedPreferencesManager
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
                    if (response.data?.data?.isNew==true && response.data.status){
                        _NavigateToRegister.value = NetworkState.getLoaded(response)
                        saveData(response)
//                        mySharedPreferences. saveUserAccessToken("${response.data.data.rememberToken}")
//                        mySharedPreferences.saveRegisterResponse(response.data.data)
                    }
                    else if (response.data?.data?.isNew==false&&response.data.status){
                        _NavigateToMain.value = NetworkState.getLoaded(response)
                        saveData(response)
//                        mySharedPreferences. saveUserAccessToken("${response.data.data.rememberToken}")
//                        mySharedPreferences.saveRegisterResponse(response.data.data)
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
    fun saveData(response: Resource<CodeResponse>){
        iSharedPreferencesManager.saveString(Constants.AVATAR,response.data?.data?.user?.avatar)
        iSharedPreferencesManager.saveString(Constants.EMAIL, response.data?.data?.user?.email)
        iSharedPreferencesManager.saveString(Constants.FULLNAME,response.data?.data?.user?.fullName)
        iSharedPreferencesManager.saveString(Constants.ISDARKMODE,response.data?.data?.user?.isDarkMode.toString())
        iSharedPreferencesManager. saveString(Constants.LANG, response.data?.data?.user?.lang)
        iSharedPreferencesManager.saveString(Constants.MOBILE, response.data?.data?.user?.mobile)
        iSharedPreferencesManager.saveString(Constants.PASSENGERCODE, response.data?.data?.user?.passengerCode)
        iSharedPreferencesManager.saveString(Constants.TOKEN, response.data?.data?.user?.rememberToken)
        iSharedPreferencesManager.saveString(Constants.STATUS,response.data?.data?.user?.status.toString())
        iSharedPreferencesManager.saveString(Constants.SUSPEND,response.data?.data?.user?.suspend.toString())

    }

}