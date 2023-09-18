package com.mostafahelal.AtmoDrive.auth.data.model.modelresponse

import com.google.gson.annotations.SerializedName
import com.mostafahelal.AtmoDrive.auth.domain.model.NewPassengerResponse

data class RegisterPassengerResponse(
    @SerializedName("data")
    val data: Data?,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
){
    public fun asDomain(): NewPassengerResponse = NewPassengerResponse(status,message, data!!.asDomain())
}
