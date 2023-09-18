package com.mostafahelal.AtmoDrive.auth.domain.use_case

import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.Resource
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.CheckCodeRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.RegisterPassengerRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.CheckCodeResponse
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.RegisterPassengerResponse
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.SendCodeResponse
import com.mostafahelal.AtmoDrive.auth.domain.model.CodeResponse
import com.mostafahelal.AtmoDrive.auth.domain.model.NewPassengerResponse
import com.mostafahelal.AtmoDrive.auth.domain.model.PhoneResponse
import kotlinx.coroutines.flow.Flow

interface IAuthUseCase  {
     suspend fun sendPhone(mobile: String): Resource<PhoneResponse>
    suspend fun checkCode( deviceToken:String,
                           mobile:String,
                           verificationCode:String)
            : Resource<CodeResponse>
    suspend fun registerPassenger(full_name:String,
                                  mobile:String,
                                  avatar:String,
                                  device_token:String,
                                  device_id:String,
                                  device_type:String
                                  ,email:String?): Resource<NewPassengerResponse>
}