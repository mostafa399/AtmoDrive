package com.mostafahelal.AtmoDrive.auth.data.data_source.remote

import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.CheckCodeRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.RegisterPassengerRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.RegisterPassengerResponse
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.SendCodeRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.CheckCodeResponse
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.SendCodeResponse
import com.mostafahelal.AtmoDrive.auth.domain.model.NewPassengerRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
const val SEND_CODE="send-code"
const val CHECK_CODE="check-code"
const val REGISTER_PASSENGER="register-passenger"
interface ApiServices {
    @FormUrlEncoded
    @POST("send-code")
    suspend fun sendCode(@Field("mobile") mobile:String):Response<SendCodeResponse>
    @POST(CHECK_CODE)
    suspend fun checkCode(@Body request: CheckCodeRequest):Response<CheckCodeResponse>
    @POST(REGISTER_PASSENGER)
    suspend fun registerPassenger(@Body request: RegisterPassengerRequest): Response<RegisterPassengerResponse>
}