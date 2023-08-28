package com.mostafahelal.AtmoDrive.auth.data.data_source.remote

import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.CheckCodeRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.RegisterPassengerRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.RegisterPassengerResponse
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.SendCodeRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.CheckCodeResponse
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.SendCodeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServices {
    @POST("send-code")
    suspend fun sendCode(@Body phone: SendCodeRequest):Response<SendCodeResponse>
    @POST("check-code")
    suspend fun checkCode(@Body request: CheckCodeRequest):Response<CheckCodeResponse>
    @POST("register-passenger")
    suspend fun registerPassenger(@Body request: RegisterPassengerRequest): Response<RegisterPassengerResponse>
}