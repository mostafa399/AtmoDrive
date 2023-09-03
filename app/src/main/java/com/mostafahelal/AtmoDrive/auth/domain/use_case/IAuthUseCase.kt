package com.mostafahelal.AtmoDrive.auth.domain.use_case

import com.mostafahelal.AtmoDrive.auth.data.data_source.Utils.Resource
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.CheckCodeRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelRequest.RegisterPassengerRequest
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.CheckCodeResponse
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.RegisterPassengerResponse
import com.mostafahelal.AtmoDrive.auth.data.model.modelresponse.SendCodeResponse
import kotlinx.coroutines.flow.Flow

interface IAuthUseCase  {
     suspend fun sendPhone(mobile: String): Resource<SendCodeResponse>
     suspend fun checkCode(code: CheckCodeRequest): Resource<CheckCodeResponse>
    suspend fun registerPassenger(passenger: RegisterPassengerRequest): Resource<RegisterPassengerResponse>

}