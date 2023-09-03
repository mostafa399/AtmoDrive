package com.mostafahelal.AtmoDrive.auth.data.data_source.remote

import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.Resource
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.CheckCodeRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.RegisterPassengerRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.RegisterPassengerResponse
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.SendCodeRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.CheckCodeResponse
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.SendCodeResponse
import com.mostafahelal.AtmoDrive.auth.domain.model.NewPassengerRequest
import com.mostafahelal.AtmoDrive.auth.domain.model.PhoneResponse
import retrofit2.Response

interface IRemoteAuth {
        suspend fun sendCode(mobile:String): Resource<SendCodeResponse>
        suspend fun checkCode( request: CheckCodeRequest): Resource<CheckCodeResponse>
        suspend fun registerPassenger( request: RegisterPassengerRequest): Resource<RegisterPassengerResponse>
    }



