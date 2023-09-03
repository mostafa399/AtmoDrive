package com.mostafahelal.AtmoDrive.auth.presentation.view_model

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.NetworkState
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.Resource
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.SharedPreference
import com.mostafahelal.AtmoDrive.auth.data.data_source.local.ISharedPrefrenceManager
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.CheckCodeRequest
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
class CheckCodeViewModel @Inject constructor(
    private val authUseCase: IAuthUseCase,
    private val sharedpreference: ISharedPrefrenceManager
):ViewModel() {
    private val _NavigateToRegister: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val navigateToRegister: StateFlow<NetworkState?> = _NavigateToRegister
    private val _NavigateToMain: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val navigateToMain: StateFlow<NetworkState?> = _NavigateToMain
    val loggedIn : String = sharedpreference.getUserToken()

    private fun saveUserAccessToken(token: String) = sharedpreference.saveUserAccessToken(token)
    fun deleteAccessToken() = sharedpreference.deleteAccessToken()
    fun checkCode(request:CheckCodeRequest){
        viewModelScope.launch {
            try {
                val response=authUseCase.checkCode(request)
                if (response.isSuccessful()){
                    if (response.data?.isNew==true &&response.data.status==1){
                        _NavigateToRegister.value = NetworkState.getLoaded(response.data.isNew)
                    }
                    else if (response.data?.data!=null&&response.data.status==1){
                        _NavigateToMain.value = NetworkState.getLoaded(response.data.data)
                        saveUserAccessToken("${response.data.data.rememberToken}")
                    }
                }else if (response.isFailed()){
                    _NavigateToMain.value = NetworkState.getErrorMessage("Check code request failed3")
                    _NavigateToRegister.value=NetworkState.getErrorMessage("Check code request failed3")

                }

            }catch (e:Exception){
                _NavigateToRegister.value = NetworkState.getErrorMessage(e)
                _NavigateToMain.value = NetworkState.getErrorMessage(e)

            }
        }
    }

//    fun checkCode(code: CheckCodeRequest){
//        authUseCase.checkCode(code = code).onEach {result->
//
//            when (result) {
//                is Resource.Error -> {
//                    Log.i("CheckCodeViewModel", "Error ${result.message}")
//                }
//                is  Resource.Success -> {
//                    if (result.data?.isNew==true &&result.data.status==1  ){
//                        _NavigateToRegister.value = NetworkState.getLoaded(result.data.isNew==true)
//
//                    }else if (result.data?.data!=null &&result.data.status==1)  {
//                        _NavigateToMain.value = NetworkState.getLoaded(result.data.isNew==null)
//                        saveUserAccessToken("${result.data.data.rememberToken}")
//                    }else{
//                        NetworkState.getErrorMessage(result.message.toString())
//                    }
//                    Log.i("CheckCodeViewModel", "Success ${result.data?.message}")
//                }
//            }
//        }.launchIn(viewModelScope)
//
//
//
//
//
//    }

}