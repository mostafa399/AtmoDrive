package com.mostafahelal.AtmoDrive.auth.data.repository
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.Resource
import com.mostafahelal.AtmoDrive.auth.data.data_source.remote.IRemoteAuth
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.CheckCodeRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.RegisterPassengerRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.CheckCodeResponse
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.RegisterPassengerResponse
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.SendCodeResponse
import com.mostafahelal.AtmoDrive.auth.domain.repository.AuthRepository
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(private val iRemoteAuth: IRemoteAuth):AuthRepository {
    override suspend fun sendCode(phone: String): Resource<SendCodeResponse> {
        try {
            val response = iRemoteAuth.sendCode(phone)
            if (response.isSuccessful() && response.data?.status == 1) {
                return Resource.Success(response.data)
            } else {
                return Resource.Error("Send code request failed")
            }
        } catch (e: Exception) {
            return Resource.Error(e.localizedMessage)
        }
    }

    override suspend fun checkCode(request: CheckCodeRequest): Resource<CheckCodeResponse> {
       try {
           val response=iRemoteAuth.checkCode(request)
           if (response.isSuccessful() && response.data?.status == 1){
               return Resource.Success(response.data)
           }else{
               return Resource.Error("Check code request failed 1 ")

           }
       }
       catch (e: Exception) {
           return Resource.Error(e.localizedMessage)
       }
    }

    override suspend fun registerPassenger(request: RegisterPassengerRequest): Resource<RegisterPassengerResponse>{
        try {
            val response=iRemoteAuth.registerPassenger(request)
            if (response.isSuccessful() && response.data?.status == 1){
               return  Resource.Success(response.data)
            }else{
              return   Resource.Error("Register request failed 1")

            }
        }
        catch (e: Exception) {
          return   Resource.Error(e.localizedMessage)
        }
    }

}

