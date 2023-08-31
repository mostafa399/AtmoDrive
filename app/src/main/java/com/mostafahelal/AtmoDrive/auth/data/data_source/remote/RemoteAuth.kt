package com.mostafahelal.AtmoDrive.auth.data.data_source.remote

import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.Resource
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.CheckCodeRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.RegisterPassengerRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.RegisterPassengerResponse
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.SendCodeRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.CheckCodeResponse
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.SendCodeResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteAuth @Inject constructor(val apiServices: ApiServices):IRemoteAuth {
    override suspend fun sendCode(mobile: String): Resource<SendCodeResponse>{
       return try {
            val response=apiServices.sendCode(mobile)
           if (response.isSuccessful&&response.body()!=null&&response.body()?.status==1) {
             Resource.Success(response.body()!!)
           }
        else{
            Resource.Error("Send code request failed")
        }
    } catch (e:Exception){
             Resource.Error(e.localizedMessage)

        }
    }

    override suspend fun checkCode(request: CheckCodeRequest):Resource<CheckCodeResponse> {
        return try {
            val response=apiServices.checkCode(request)
            if (response.isSuccessful&&response.body() !=null&&response.body()?.status==1&&request.mobile.length==11){
                Resource.Success(response.body()!!)
            }else{
                Resource.Error("Check code is expired")
            }

        }
        catch (e:Exception){
            Resource.Error(e.localizedMessage)

        }
    }

    override suspend fun registerPassenger(request: RegisterPassengerRequest): Resource<RegisterPassengerResponse> {
        return try {
            val response=apiServices.registerPassenger(request=request)
            if (response.isSuccessful&&response.body() !=null){
            Resource.Success(response.body()!!)
        }else{
            Resource.Error("Send code request failed")
        }

    }catch (e:Exception){
            Resource.Error(e.localizedMessage)

        }
       // return apiServices.registerPassenger(request=request)
    }
}