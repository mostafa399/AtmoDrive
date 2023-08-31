package com.mostafahelal.AtmoDrive.auth.presentation.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.NetworkState
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.Resource
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.SharedPreference
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.CheckCodeRequest
import com.mostafahelal.AtmoDrive.auth.domain.use_case.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CheckCodeViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val sharedPreference: SharedPreference
):ViewModel() {
    private val _NavigateToRegister: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val navigateToRegister: StateFlow<NetworkState?> = _NavigateToRegister
    private val _NavigateToMain: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val navigateToMain: StateFlow<NetworkState?> = _NavigateToMain


    private fun saveUserAccessToken(token:String)=sharedPreference.saveUserAccessToken(token)
    fun checkCode(code: CheckCodeRequest){
        authUseCase.CheckCode(code = code).onEach {result->

                     when (result) {
                         is Resource.Error -> {
                             Log.i("CheckCodeViewModel", "Error ${result.message}")

                         }
                         is  Resource.Success -> {
                             _NavigateToRegister.value = NetworkState.getLoaded(result)
                             if (result.data?.isNew!=null  ){
                                 _NavigateToRegister.value = NetworkState.getLoaded(result)
                             }else if (result.data?.data!=null)  {
                                 _NavigateToMain.value = NetworkState.getLoaded(result)
                             }else{
                                NetworkState.getErrorMessage(result.message.toString())
                             }

                           //  successful.postValue(true)
                            // saveUserAccessToken("${result.data?.data?.rememberToken}")
                             Log.i("CheckCodeViewModel", "Success ${result.data?.message}")
                         }
                     }
            }.launchIn(viewModelScope)




    }

//    fun navigationToPage(){
//        successful.postValue(null)
//        error.postValue(null)
//    }



}