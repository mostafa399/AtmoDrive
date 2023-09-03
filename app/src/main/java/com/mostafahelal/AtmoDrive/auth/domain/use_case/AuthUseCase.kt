package com.mostafahelal.AtmoDrive.auth.domain.use_case

import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.Resource
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.CheckCodeRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.RegisterPassengerRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.CheckCodeResponse
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.RegisterPassengerResponse
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.SendCodeResponse
import com.mostafahelal.AtmoDrive.auth.domain.model.NewPassengerRequest
import com.mostafahelal.AtmoDrive.auth.domain.model.PhoneResponse
import com.mostafahelal.AtmoDrive.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
):IAuthUseCase {
    override suspend fun sendPhone(mobile: String): Resource<SendCodeResponse> {
        return try {
            val response = authRepository.sendCode(mobile)
            if (response.isSuccessful() && response.data?.status == 1) {
                Resource.Success(response.data)
            } else {
                Resource.Error("Send code request failed")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage)
        }
    }
    override suspend fun checkCode(code:CheckCodeRequest):Resource<CheckCodeResponse> {
            return try {
                val response=authRepository.checkCode(request = code)
                if (response.isSuccessful() && response.data?.status == 1) {
                    Resource.Success(response.data)
                }else {
                    Resource.Error("Check code request failed2")
                }
            }
            catch (e: Exception) {
                Resource.Error(e.localizedMessage)
            }

    }
    override suspend fun registerPassenger(passenger:RegisterPassengerRequest):Resource<RegisterPassengerResponse> {
        return try {
            val response = authRepository.registerPassenger(request = passenger)
            if (response.isSuccessful() && response.data?.status == 1) {
                Resource.Success(response.data)
            }else {
                Resource.Error("Register request failed2")
            }
        }
        catch (e: Exception) {
            Resource.Error(e.localizedMessage)
        }

    }


}