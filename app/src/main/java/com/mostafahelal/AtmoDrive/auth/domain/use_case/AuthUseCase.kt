package com.mostafahelal.AtmoDrive.auth.domain.use_case

import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.Resource
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.CheckCodeRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.RegisterPassengerRequest
import com.mostafahelal.AtmoDrive.auth.domain.model.CodeResponse
import com.mostafahelal.AtmoDrive.auth.domain.model.NewPassengerResponse
import com.mostafahelal.AtmoDrive.auth.domain.model.PhoneResponse
import com.mostafahelal.AtmoDrive.auth.domain.repository.AuthRepository
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
):IAuthUseCase {
    override suspend fun sendPhone(mobile: String): Resource<PhoneResponse> {

           return authRepository.sendCode(mobile)

    }
    override suspend fun checkCode(deviceToken:String,
                                   mobile:String,
                                   verificationCode:String):Resource<CodeResponse> {

              return authRepository.checkCode(deviceToken,mobile,verificationCode)

    }
    override suspend fun registerPassenger(full_name:String,
                                           mobile:String,
                                           avatar:String,
                                           device_token:String,
                                           device_id:String,
                                           device_type:String,
                                           email:String?):Resource<NewPassengerResponse> {

        return authRepository.registerPassenger(full_name, mobile, avatar, device_token, device_id, device_type,email)

    }


}