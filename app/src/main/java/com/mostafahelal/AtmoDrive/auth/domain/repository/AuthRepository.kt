package com.mostafahelal.AtmoDrive.auth.domain.repository

import com.mostafahelal.AtmoDrive.Utils.Resource
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.CheckCodeRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.RegisterPassengerRequest
import com.mostafahelal.AtmoDrive.auth.domain.model.CodeResponse
import com.mostafahelal.AtmoDrive.auth.domain.model.NewPassengerResponse
import com.mostafahelal.AtmoDrive.auth.domain.model.PhoneResponse

interface AuthRepository {
    suspend fun sendCode(phone: String): Resource<PhoneResponse>
    suspend fun checkCode( deviceToken:String,
                           mobile:String,
                           verificationCode:String)
    : Resource<CodeResponse>
    suspend fun registerPassenger(full_name:String,
                                  mobile:String,
                                  avatar:String,
                                  device_token:String,
                                  device_id:String,
                                  device_type:String,email:String?): Resource<NewPassengerResponse>
}