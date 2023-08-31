package com.mostafahelal.AtmoDrive.auth.domain.use_case

import android.util.Log
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.NetworkState
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.Resource
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.CheckCodeRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.RegisterPassengerRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.SendCodeRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.CheckCodeResponse
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.RegisterPassengerResponse
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.SendCodeResponse
import com.mostafahelal.AtmoDrive.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    fun sendPhone(phone:String):Flow<Resource<SendCodeResponse>>
    = flow {
        val response=authRepository.sendCode(phone)
        if (response.isSuccessful()){
            emit(response)
        }else if (response.isFailed()){
            emit(
                Resource.Error(
                    response.message
                )
            )
        }

    }
    fun CheckCode(code:CheckCodeRequest):Flow<Resource<CheckCodeResponse>>
    = flow {
        val response=authRepository.checkCode(code)
        if (response.isSuccessful()) {
            emit(response)
        }

       else if (response.isFailed()) {
            emit(
                Resource.Error(
                    response.message
                )
            )
        }

    }
    fun RegisterPassenger(passenger:RegisterPassengerRequest):Flow<Resource<RegisterPassengerResponse>> = flow {
            val response=authRepository.registerPassenger(request = passenger)
            if (response.isSuccessful()){
            emit(response)
            }
            else if (response.isFailed()){
                emit(Resource.Error(
                    response.message
                ))
            }


    }


}