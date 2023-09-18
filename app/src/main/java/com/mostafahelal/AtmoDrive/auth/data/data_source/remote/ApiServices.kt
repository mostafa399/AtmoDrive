package com.mostafahelal.AtmoDrive.auth.data.data_source.remote

import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.CheckCodeRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.RegisterPassengerRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.RegisterPassengerResponse
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.CheckCodeResponse
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.SendCodeResponse
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
    @FormUrlEncoded
    @POST(CHECK_CODE)
    suspend fun checkCode(@Field("device_token")deviceToken:String,
                          @Field("mobile")mobile:String,
                          @Field("verification_code")verificationCode:String
                          ):Response<CheckCodeResponse>

    @FormUrlEncoded
    @POST(REGISTER_PASSENGER)
    suspend fun registerPassenger(@Field("full_name")full_name:String,
                                  @Field("mobile")mobile:String,
                                  @Field("avatar")avatar:String,
                                  @Field("device_token")device_token:String,
                                  @Field("device_id")device_id:String,
                                  @Field("device_type")device_type:String,
                                  @Field("email")email:String?

    ): Response<RegisterPassengerResponse>
}