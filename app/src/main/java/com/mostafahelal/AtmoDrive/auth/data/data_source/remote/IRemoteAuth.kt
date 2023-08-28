package com.mostafahelal.AtmoDrive.auth.data.data_source.remote

import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.CheckCodeRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.RegisterPassengerRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.RegisterPassengerResponse
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.SendCodeRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.CheckCodeResponse
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.SendCodeResponse
import retrofit2.Response

interface IRemoteAuth {
    suspend fun sendCode(request: SendCodeRequest): Response<SendCodeResponse>
    suspend fun checkCode( request: CheckCodeRequest):Response<CheckCodeResponse>
    suspend fun registerPassenger( request: RegisterPassengerRequest): Response<RegisterPassengerResponse>
}


