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
    private fun responseCheckCode(response: Response<CheckCodeResponse>) : Resource<CheckCodeResponse>{
        if (response.isSuccessful){
            response.body()?.let { result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(message = "${response.errorBody()?.string()}")
    }
    private fun responseRegister(response : Response<RegisterPassengerResponse>) : Resource<RegisterPassengerResponse>{
        if (response.isSuccessful){
            response.body()?.let { result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(message = "${response.errorBody()?.string()}")
    }

    private fun responseSendCode(response: Response<SendCodeResponse>) : Resource<SendCodeResponse>{
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(message = "${response.errorBody()?.string()}")
    }
    override suspend fun sendCode(request: SendCodeRequest): Resource<SendCodeResponse> {
        return responseSendCode(iRemoteAuth.sendCode(request=request))
    }

    override suspend fun checkCode(request: CheckCodeRequest): Resource<CheckCodeResponse> {
      return responseCheckCode(iRemoteAuth.checkCode(request=request))
    }

    override suspend fun registerPassenger(request: RegisterPassengerRequest): Resource<RegisterPassengerResponse> {
        return responseRegister(iRemoteAuth.registerPassenger(request=request))
    }

}


