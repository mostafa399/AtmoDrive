package com.mostafahelal.AtmoDrive.auth.data.repository
import com.mostafahelal.AtmoDrive.Utils.Resource
import com.mostafahelal.AtmoDrive.auth.data.data_source.remote.IRemoteAuth
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.CheckCodeRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.RegisterPassengerRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.CheckCodeResponse
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.RegisterPassengerResponse
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.SendCodeResponse
import com.mostafahelal.AtmoDrive.auth.domain.model.CodeResponse
import com.mostafahelal.AtmoDrive.auth.domain.model.NewPassengerResponse
import com.mostafahelal.AtmoDrive.auth.domain.model.PhoneResponse
import com.mostafahelal.AtmoDrive.auth.domain.repository.AuthRepository
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(private val iRemoteAuth: IRemoteAuth):AuthRepository {
    override suspend fun sendCode(phone: String): Resource<PhoneResponse> {

           return iRemoteAuth.sendCode(phone)

    }

    override suspend fun checkCode(deviceToken:String,
                                   mobile:String,
                                   verificationCode:String): Resource<CodeResponse> {
           return iRemoteAuth.checkCode(deviceToken,mobile,verificationCode)

       }


    override suspend fun registerPassenger(full_name:String,
                                           mobile:String,
                                           avatar:String,
                                           device_token:String,
                                           device_id:String,
                                           device_type:String,
                                           email:String?
    ): Resource<NewPassengerResponse> {
            return iRemoteAuth.registerPassenger(full_name,mobile,avatar,device_token,device_id,device_type,email)

    }

}

