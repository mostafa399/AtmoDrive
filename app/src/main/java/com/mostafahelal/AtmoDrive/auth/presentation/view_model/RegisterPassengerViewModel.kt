package com.mostafahelal.AtmoDrive.auth.presentation.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.Resource
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.SharedPreference
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.CheckCodeRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.RegisterPassengerRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.SendCodeRequest
import com.mostafahelal.AtmoDrive.auth.domain.use_case.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterPassengerViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val sharedPreference: SharedPreference
):ViewModel() {

    val successful: MutableLiveData<Boolean?> = MutableLiveData()
    val error: MutableLiveData<String?> = MutableLiveData()

    fun registerPassenger(Passenger: RegisterPassengerRequest){
        authUseCase.RegisterPassenger(passenger = Passenger).onEach {result->
            when (result) {
                is Resource.Loading -> {
                    Log.i("LoginViewModel", "I dey here, Loading")
                }
                is Resource.Error -> {
                    error.postValue("${result.message}")
                    successful.postValue(false)
                    Log.i("LoginViewModel", "I dey here, Error ${result.message}")

                }
                is Resource.Success -> {
                    successful.postValue(true)
                    Log.i("LoginViewModel", "I dey here, Success ${result.data?.data?.status}")
                }
            }


        }.launchIn(viewModelScope)
    }
    fun navigationToPage(){
        successful.postValue(null)
        error.postValue(null)
    }


}