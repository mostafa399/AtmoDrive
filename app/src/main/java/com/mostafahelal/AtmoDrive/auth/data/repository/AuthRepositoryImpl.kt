package com.mostafahelal.AtmoDrive.auth.data.repository
import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.Resource
import com.mostafahelal.AtmoDrive.auth.data.data_source.remote.IRemoteAuth
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.CheckCodeRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.RegisterPassengerRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.SendCodeRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.CheckCodeResponse
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.RegisterPassengerResponse
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.SendCodeResponse
import com.mostafahelal.AtmoDrive.auth.domain.repository.AuthRepository
import retrofit2.Response
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(private val iRemoteAuth: IRemoteAuth):AuthRepository {
    override suspend fun sendCode(phone: String): Resource<SendCodeResponse> {
        val response=iRemoteAuth.sendCode(phone)
        return response
    }

    override suspend fun checkCode(request: CheckCodeRequest): Resource<CheckCodeResponse> {
        val response=iRemoteAuth.checkCode(request)
        return response
    }

    override suspend fun registerPassenger(request: RegisterPassengerRequest): Resource<RegisterPassengerResponse>{
        val response=iRemoteAuth.registerPassenger(request=request)
        return response
    }

}


