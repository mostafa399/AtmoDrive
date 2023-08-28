package com.mostafahelal.AtmoDrive.auth.domain.use_case

import android.util.Log
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.Resource
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.CheckCodeRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.RegisterPassengerRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.SendCodeRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.CheckCodeResponse
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.RegisterPassengerResponse
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.SendCodeResponse
import com.mostafahelal.AtmoDrive.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    fun sendPhone(phone:SendCodeRequest):Flow<Resource<SendCodeResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response=authRepository.sendCode(request = phone)
            emit(response)
        }catch  (e: HttpException){
            Log.i("AuthUseCase", e.localizedMessage!!)
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }catch (e: IOException){
            Log.i("AuthUseCase", e.localizedMessage!!)
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))

        }


    }
    fun CheckCode(code:CheckCodeRequest):Flow<Resource<CheckCodeResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response=authRepository.checkCode(request = code)
            emit(response)
        }catch  (e: HttpException){
            Log.i("AuthUseCase", e.localizedMessage!!)
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }catch (e: IOException){
            Log.i("AuthUseCase", e.localizedMessage!!)
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))

        }


    }
    fun RegisterPassenger(passenger:RegisterPassengerRequest):Flow<Resource<RegisterPassengerResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response=authRepository.registerPassenger(request = passenger)
            emit(response)
        }catch  (e: HttpException){
            Log.i("AuthUseCase", e.localizedMessage!!)
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }catch (e: IOException){
            Log.i("AuthUseCase", e.localizedMessage!!)
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))

        }


    }


}