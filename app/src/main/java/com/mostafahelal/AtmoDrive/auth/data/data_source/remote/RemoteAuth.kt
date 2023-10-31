package com.mostafahelal.AtmoDrive.auth.data.data_source.remote

import com.mostafahelal.AtmoDrive.Utils.Resource
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.CheckCodeRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.RegisterPassengerRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.RegisterPassengerResponse
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.CheckCodeResponse
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.SendCodeResponse
import com.mostafahelal.AtmoDrive.auth.domain.model.CodeResponse
import com.mostafahelal.AtmoDrive.auth.domain.model.NewPassengerResponse
import com.mostafahelal.AtmoDrive.auth.domain.model.PhoneResponse
import javax.inject.Inject
class RemoteAuth @Inject constructor(val apiServices: ApiServices):IRemoteAuth {
    override suspend fun sendCode(mobile: String): Resource<PhoneResponse> {
        return try {
            val response=apiServices.sendCode(mobile)
            if (response.isSuccessful&&response.body()!=null&&response.body()?.status==true) {
                Resource.Success(response.body()!!.asDomain())
            }
            else{
                Resource.Error("Send code request failed")
            }
        } catch (e:Exception){
            Resource.Error(e.localizedMessage)

        }
    }

    override suspend fun checkCode(deviceToken:String,
                                   mobile:String,
                                   verificationCode:String): Resource<CodeResponse> {
        return try {
            val response=apiServices.checkCode(deviceToken,mobile,verificationCode)
            if (response.isSuccessful&&response.body()!=null&&response.body()?.status==true){
                Resource.Success(response.body()!!.asDomain())
            }else{
                Resource.Error("Check code is expired")
            }

        }
        catch (e:Exception){
            Resource.Error(e.localizedMessage)

        }
    }

    override suspend fun registerPassenger(full_name:String,
                                           mobile:String,
                                           avatar:String,
                                           device_token:String,
                                           device_id:String,
                                           device_type:String,
                                           email:String?): Resource<NewPassengerResponse> {
        return try {
            val response=apiServices.registerPassenger(full_name,mobile,avatar,device_token,device_id,device_type,email)
            if (response.isSuccessful&&response.body() !=null){
                Resource.Success(response.body()!!.asDomain())
            }else{
                Resource.Error("Register code request failed")
            }

        }catch (e:Exception){
            Resource.Error(e.localizedMessage)

        }
    }
}