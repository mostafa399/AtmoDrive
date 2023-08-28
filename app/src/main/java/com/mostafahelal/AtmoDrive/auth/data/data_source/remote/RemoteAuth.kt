package com.mostafahelal.AtmoDrive.auth.data.data_source.remote

import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.CheckCodeRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.RegisterPassengerRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.RegisterPassengerResponse
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.SendCodeRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.CheckCodeResponse
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.SendCodeResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteAuth @Inject constructor(val apiServices: ApiServices):IRemoteAuth {
    override suspend fun sendCode(request: SendCodeRequest): Response<SendCodeResponse> {
       return apiServices.sendCode(phone = request)
    }

    override suspend fun checkCode(request: CheckCodeRequest): Response<CheckCodeResponse> {
        return apiServices.checkCode(request=request)
    }

    override suspend fun registerPassenger(request: RegisterPassengerRequest): Response<RegisterPassengerResponse> {
        return apiServices.registerPassenger(request=request)
    }
}